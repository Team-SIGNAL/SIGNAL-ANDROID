package com.signal.signal_android.feature.main.home

data class HomeState(
    val lastDiagnosisDate: String,
) {
    companion object {
        fun getDefaultState() = HomeState(
            lastDiagnosisDate = "",
        )
    }
}
