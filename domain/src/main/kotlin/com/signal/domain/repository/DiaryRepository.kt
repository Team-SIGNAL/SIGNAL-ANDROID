package com.signal.domain.repository

import com.signal.domain.entity.FetchAllDiaryEntity
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.entity.FetchMonthDiaryEntity
import com.signal.domain.enums.Emotion

interface DiaryRepository {
    suspend fun fetchAllDiary(): FetchAllDiaryEntity
    suspend fun fetchMonthDiary(date: String): FetchMonthDiaryEntity
    suspend fun fetchDayDiary(date: String): FetchDayDiaryEntity
    suspend fun createDiary(
        title: String,
        content: String,
        emotion: Emotion,
        date: String,
        image: String?,
    ): Result<Unit>
}