package com.signal.domain.repository

import com.signal.domain.entity.AllDiaryEntity
import com.signal.domain.entity.DayDiaryEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion

interface DiaryRepository {
    suspend fun fetchAllDiary(): AllDiaryEntity
    suspend fun fetchMonthDiary(date: String): MonthDiaryEntity
    suspend fun fetchDayDiary(date: String): DayDiaryEntity
    suspend fun createDiary(
        title: String,
        content: String,
        emotion: Emotion,
        date: String,
        image: String?,
    ): Result<Unit>
}