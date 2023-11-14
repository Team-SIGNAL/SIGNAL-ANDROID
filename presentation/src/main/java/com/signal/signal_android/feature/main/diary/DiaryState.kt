package com.signal.signal_android.feature.main.diary

import com.signal.domain.entity.DiariesEntity
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion
import java.time.LocalDate

data class DiaryState(
    val diaries: List<DiariesEntity.DiaryEntity>,
    val monthDiaries: List<MonthDiaryEntity.MonthDiaryEntity>,
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
            diaries = listOf(),
            monthDiaries = listOf(),
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