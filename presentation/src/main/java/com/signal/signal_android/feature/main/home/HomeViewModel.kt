package com.signal.signal_android.feature.main.home

import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.DiagnosisHistoryEntity
import com.signal.domain.enums.ChartViewType
import com.signal.domain.usecase.users.GetAccountIdUseCase
import com.signal.domain.usecase.users.GetDiagnosisHistoriesUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getDiagnosisHistoriesUseCase: GetDiagnosisHistoriesUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {
    private val diagnosisHistories: MutableList<DiagnosisHistoryEntity> = mutableListOf()

    init {
        getAccountId()
    }

    private fun getAccountId() {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountIdUseCase().onSuccess {
                getDiagnosisHistories(userId = it)
            }
        }
    }

    private fun getDiagnosisHistories(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getDiagnosisHistoriesUseCase(userId = userId).onSuccess { it ->
                diagnosisHistories.addAll(it)
                setState(
                    state.value.copy(
                        diagnosisHistories = diagnosisHistories,
                        lastDiagnosisDate = diagnosisHistories.lastOrNull()?.run {
                            "${year}-${month}-${day.toString().padStart(2, '0')}"
                        }?.toLastDiagnosisDate()
                            ?: "",
                    )
                )
            }
        }
    }

    private fun String.toLastDiagnosisDate() = StringBuilder().apply {
        append(this@toLastDiagnosisDate.substring(0..3))
        append("년 ")
        append(this@toLastDiagnosisDate.substring(5..6))
        append("월 ")
        append(this@toLastDiagnosisDate.substring(8..9))
        append("일 ")
    }.toString()

    internal fun nextChartViewType() {
        with(state.value) {
            val currentChartViewTypeIndex = chartViewType.ordinal
            setState(
                copy(
                    chartViewType = if (currentChartViewTypeIndex != ChartViewType.values().lastIndex) {
                        ChartViewType.values()[currentChartViewTypeIndex + 1]
                    } else {
                        ChartViewType.values().first()
                    }
                )
            )
        }
    }

    internal fun previousChartViewType() {
        with(state.value) {
            val currentChartViewTypeIndex = chartViewType.ordinal
            setState(
                copy(
                    chartViewType = if (currentChartViewTypeIndex != 0) {
                        ChartViewType.values()[currentChartViewTypeIndex - 1]
                    } else {
                        ChartViewType.values().last()
                    }
                )
            )
        }
    }
}
