package com.signal.signal_android.feature.diagnosis

import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.DiagnosisEntity
import com.signal.domain.entity.DiagnosisHistoryEntity
import com.signal.domain.repository.DiagnosisRepository
import com.signal.domain.usecase.users.GetAccountIdUseCase
import com.signal.domain.usecase.users.GetDiagnosisHistoriesUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

internal class DiagnosisViewModel(
    private val diagnosisRepository: DiagnosisRepository,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val getDiagnosisHistoriesUseCase: GetDiagnosisHistoriesUseCase,
) : BaseViewModel<DiagnosisState, DiagnosisSideEffect>(DiagnosisState.getDefaultState()) {

    private val diagnosis: MutableList<DiagnosisEntity> = mutableListOf()
    private val diagnosisHistories: MutableList<DiagnosisHistoryEntity> = mutableListOf()

    init {
        getDiagnosis()
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
                val date = LocalDate.now().toString()
                val score = diagnosis.sumOf { it.score ?: 0L }
                if (diagnosisHistories.none { it.date == date }) {
                    diagnosisRepository.addDiagnosisHistory(
                        DiagnosisHistoryEntity(
                            id = if (diagnosisHistories.isEmpty()) 0
                            else diagnosisHistories.last().id + 1,
                            score = score,
                            userId = accountId,
                            date = date,
                        )
                    )
                } else {
                    diagnosisRepository.setDiagnosisHistory(
                        diagnosisHistories.first { it.date == date }.copy(
                            score = score,
                        )
                    )
                }
            }
        }
    }
}
