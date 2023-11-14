package com.signal.signal_android.feature.main.diary

import com.signal.domain.entity.AllDiaryEntity
import com.signal.domain.entity.DayDiaryEntity
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion
import java.time.LocalDate

data class DiaryState(
    val allDiaries: List<AllDiaryEntity.AllDiaryEntity>,
    val monthDiaries: List<MonthDiaryEntity.MonthDiaryEntity>,
    val dayDiaries: List<DayDiaryEntity.DayDiaryEntity>,
    val diaryDetailsEntity: DiaryDetailsEntity,
    val isAllDiariesEmpty: Boolean,
    val isMonthDiariesEmpty: Boolean,
    val isDayDiariesEmpty: Boolean,
    val title: String,
    val content: String,
    val emotion: Emotion,
    var date: String,
    val image: String?,
    val diaryId: Long,
) {
    companion object {
        fun getDefaultState() = DiaryState(
            allDiaries = listOf(),
            monthDiaries = listOf(),
            dayDiaries = listOf(),
            diaryDetailsEntity = DiaryDetailsEntity(
                date = "",
                title = "",
                content = "",
                emotion = Emotion.HAPPY,
                image = null,
            ),
            isAllDiariesEmpty = true,
            isMonthDiariesEmpty = true,
            isDayDiariesEmpty = true,
            title = "",
            content = "",
            emotion = Emotion.HAPPY,
            date = LocalDate.now().toString(),
            image = null,
            diaryId = 0L,
        )
    }
}