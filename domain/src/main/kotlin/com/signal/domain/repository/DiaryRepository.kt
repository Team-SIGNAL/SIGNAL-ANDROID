package com.signal.domain.repository

import com.signal.domain.entity.DiariesEntity
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion

interface DiaryRepository {
    suspend fun fetchAllDiary(): DiariesEntity
    suspend fun fetchMonthDiary(date: String): MonthDiaryEntity
    suspend fun fetchDayDiary(date: String): DiariesEntity
    suspend fun createDiary(
        title: String,
        content: String,
        emotion: Emotion,
        date: String,
        image: String?,
    ): Result<Unit>

    suspend fun fetchDiaryDetails(diaryId: Long): Result<DiaryDetailsEntity>
}