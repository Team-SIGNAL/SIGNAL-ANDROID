package com.signal.signal_android.feature.diagnosis

import com.signal.domain.entity.DiagnosisEntity

data class DiagnosisState(
    val size: Int,
    val diagnosis: List<DiagnosisEntity>,
    val count: Int,
    val accountId: String,
) {
    companion object {
        fun getDefaultState() = DiagnosisState(
            size = 0,
            diagnosis = listOf(
                DiagnosisEntity(
                    id = 0L,
                    question = "",
                    score = null,
                )
            ),
            count = 0,
            accountId = "",
        )
    }
}
