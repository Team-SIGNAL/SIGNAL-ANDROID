package com.signal.signal_android.feature.diagnosis

import com.signal.domain.entity.DiagnosisEntity

data class DiagnosisState(
    val size: Int,
    val diagnosis: List<DiagnosisEntity>,
    val count: Int,
) {
    companion object {
        fun getDefaultState() = DiagnosisState(
            size = 0,
            diagnosis = listOf(
                DiagnosisEntity(
                    id = 0L,
                    question = "",
                    score = 0L,
                ),
            ),
            count = 0,
        )
    }
}
