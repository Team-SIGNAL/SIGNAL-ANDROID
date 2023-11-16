package com.signal.signal_android.feature.main.home

import com.signal.domain.entity.DiagnosisHistoryEntity
import com.signal.domain.enums.ChartViewType

data class HomeState(
    val lastDiagnosisDate: String,
    val chartViewType: ChartViewType,
    val diagnosisHistories: List<DiagnosisHistoryEntity>,
) {
    companion object {
        fun getDefaultState() = HomeState(
            lastDiagnosisDate = "",
            chartViewType = ChartViewType.DAY,
            diagnosisHistories = emptyList(),
        )
    }
}
