package com.signal.signal_android.feature.diagnosis

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.DiagnosisEntity
import com.signal.domain.entity.DiagnosisHistoryEntity
import com.signal.domain.repository.DiagnosisRepository
import com.signal.domain.usecase.users.GetAccountIdUseCase
import com.signal.domain.usecase.users.GetDiagnosisHistoriesUseCase
import com.signal.domain.usecase.users.GetHistoryCountUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

internal class DiagnosisViewModel(
    private val diagnosisRepository: DiagnosisRepository,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val getDiagnosisHistoriesUseCase: GetDiagnosisHistoriesUseCase,
    private val getHistoryCountUseCase: GetHistoryCountUseCase,
) : BaseViewModel<DiagnosisState, DiagnosisSideEffect>(DiagnosisState.getDefaultState()) {

    private val diagnosis: MutableList<DiagnosisEntity> = mutableListOf()
    private val diagnosisHistories: MutableList<DiagnosisHistoryEntity> = mutableListOf()

    init {
        getDiagnosis()
        getHistoryCount()
        getAccountId()
        getDiagnosisHistories()
    }

    private fun getDiagnosisHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            getDiagnosisHistoriesUseCase(userId = state.value.accountId).onSuccess {
                diagnosisHistories.addAll(it)
            }
        }
    }

    private fun getDiagnosis() {
        viewModelScope.launch(Dispatchers.IO) {
            diagnosisRepository.getDiagnosis().onSuccess {
                diagnosis.addAll(it)
                setState(
                    state.value.copy(
                        size = it.size,
                        diagnosis = diagnosis,
                    ),
                )
            }
        }
    }

    private fun getHistoryCount() {
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryCountUseCase().onSuccess {
                setState(state.value.copy(historyCount = it))
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    private fun getAccountId() {
        getAccountIdUseCase().onSuccess {
            setState(state.value.copy(accountId = it))
        }
    }

    internal fun setCount(count: Int) {
        setState(state.value.copy(count = count))
    }

    internal fun setScore(score: Long?) {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                diagnosisRepository.setDiagnosis(diagnosisEntity = diagnosis[count].copy(score = score))
            }
            this@DiagnosisViewModel.diagnosis.set(count, diagnosis[count].copy(score = score))
        }
    }

    internal fun addDiagnosisHistory() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                val date = LocalDate.now()

                val year = date.year
                val month = date.monthValue
                val day = date.dayOfMonth

                val week = Calendar.getInstance().run {
                    set(year, month - 1, day)
                    get(Calendar.WEEK_OF_YEAR)
                }

                val score = diagnosis.sumOf { it.score ?: 0L }
                if (diagnosisHistories.none {
                        it.year == year &&
                                it.month == month &&
                                it.day == day
                    }) {
                    diagnosisRepository.addDiagnosisHistory(
                        DiagnosisHistoryEntity(
                            id = historyCount,
                            score = score,
                            userId = accountId,
                            year = year,
                            month = month,
                            day = day,
                            week = week,
                        )
                    )
                } else {
                    diagnosisRepository.setDiagnosisHistory(
                        diagnosisHistories.first {
                            it.year == year &&
                                    it.month == month &&
                                    it.day == day
                        }.copy(score = score)
                    )
                }
            }
        }
    }
}
