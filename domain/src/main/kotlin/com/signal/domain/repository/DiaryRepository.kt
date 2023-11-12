package com.signal.domain.repository

import com.signal.domain.entity.FetchAllDiaryEntity
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.entity.FetchMonthDiaryEntity
import com.signal.domain.enums.Emotion
import java.time.LocalDateTime

interface DiaryRepository {
    suspend fun fetchAllDiary(): FetchAllDiaryEntity
    suspend fun fetchMonthDiary(date: LocalDateTime): FetchMonthDiaryEntity
    suspend fun fetchDayDiary(date: LocalDateTime): FetchDayDiaryEntity
    suspend fun createDiary(
        title: String,
        content: String,
        emotion: Emotion,
        date: LocalDateTime,
        image: String?,
    ): Result<Unit>
}