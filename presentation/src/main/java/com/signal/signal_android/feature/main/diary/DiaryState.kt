package com.signal.signal_android.feature.main.diary

import androidx.compose.material.BottomSheetState
import com.signal.domain.enums.Emotion

data class DiaryState(
    val title: String,
    val content: String,
    val emotion: Emotion,
    val date: String,
    val image: String?,
    val bottomSheetState: BottomSheetState,
) {
    companion object {
        fun getDefaultState() = DiaryState(
            title = "",
            content = "",
            emotion = Emotion.HAPPY,
            date = "",
            image = null,
            bottomSheetState = false,
        )
    }
}