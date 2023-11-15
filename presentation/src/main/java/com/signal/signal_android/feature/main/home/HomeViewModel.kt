package com.signal.signal_android.feature.main.home

import com.signal.domain.enums.ChartViewType
import com.signal.domain.repository.DiagnosisRepository
import com.signal.signal_android.BaseViewModel

internal class HomeViewModel(
    private val diagnosisRepository: DiagnosisRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {
    init {
        getLastDiagnosisDate()
    }

    private fun getLastDiagnosisDate() {
        diagnosisRepository.getLastDiagnosisDate().onSuccess {
            setState(state.value.copy(lastDiagnosisDate = it))
        }.onFailure { }
    }

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
