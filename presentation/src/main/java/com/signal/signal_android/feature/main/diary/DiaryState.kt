package com.signal.signal_android.feature.main.diary

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.signal.domain.entity.DiariesEntity
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion
import java.time.LocalDate
import java.util.UUID

data class DiaryState(
    val diaries: SnapshotStateList<DiariesEntity.DiaryEntity>,
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
    val diaryId: UUID,
) {
    companion object {
        fun getDefaultState() = DiaryState(
            diaries = mutableStateListOf(),
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
            diaryId = UUID.randomUUID(),
        )
    }
}