package com.signal.signal_android.feature.main.diary

import com.signal.domain.entity.FetchAllDiaryEntity
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.entity.FetchMonthDiaryEntity
import com.signal.domain.enums.Emotion
import java.time.LocalDateTime

data class DiaryState(
    val allDiaries: List<FetchAllDiaryEntity.AllDiaryEntity>,
    val monthDiaries: List<FetchMonthDiaryEntity.MonthDiaryEntity>,
    val dayDiaries: List<FetchDayDiaryEntity.DayDiaryEntity>,
    val isAllDiariesEmpty: Boolean,
    val isMonthDiariesEmpty: Boolean,
    val isDayDiariesEmpty: Boolean,
    val title: String,
    val content: String,
    val emotion: Emotion,
    val date: LocalDateTime,
    val image: String?,
) {
    companion object {
        fun getDefaultState() = DiaryState(
            allDiaries = listOf(),
            monthDiaries = listOf(),
            dayDiaries = listOf(),
            isAllDiariesEmpty = true,
            isMonthDiariesEmpty = true,
            isDayDiariesEmpty = true,
            title = "",
            content = "",
            emotion = Emotion.HAPPY,
            date = LocalDateTime.now(),
            image = null,
        )
    }
}