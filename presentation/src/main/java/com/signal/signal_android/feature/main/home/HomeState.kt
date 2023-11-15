package com.signal.signal_android.feature.main.home

import com.signal.domain.enums.ChartViewType

data class HomeState(
    val lastDiagnosisDate: String,
    val chartViewType: ChartViewType,
) {
    companion object {
        fun getDefaultState() = HomeState(
            lastDiagnosisDate = "",
            chartViewType = ChartViewType.DAY,
        )
    }
}
