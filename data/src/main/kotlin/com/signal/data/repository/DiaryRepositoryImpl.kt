package com.signal.data.repository

import com.signal.data.datasource.diary.DiaryDataSource
import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.toEntity
import com.signal.domain.entity.AllDiaryEntity
import com.signal.domain.entity.DayDiaryEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion
import com.signal.domain.repository.DiaryRepository

class DiaryRepositoryImpl(
    private val diaryDateSource: DiaryDataSource,
) : DiaryRepository {
    override suspend fun fetchAllDiary(): AllDiaryEntity =
        diaryDateSource.fetchAllDiary().toEntity()

    override suspend fun fetchMonthDiary(
        date: String
    ): MonthDiaryEntity = diaryDateSource.fetchMonthDiary(
        date = date
    ).toEntity()

    override suspend fun fetchDayDiary(
        date: String
    ): DayDiaryEntity = diaryDateSource.fetchDayDiary(
        date = date
    ).toEntity()

    override suspend fun createDiary(
        title: String,
        content: String,
        emotion: Emotion,
        date: String,
        image: String?,
    ) = runCatching {
        diaryDateSource.createDiary(
            CreateDiaryRequest(
                title = title,
                content = content,
                emotion = emotion,
                date = date,
                image = image,
            )
        )
    }

    override suspend fun fetchDiaryDetails(diaryId: Long) = runCatching {
        diaryDateSource.fetchDiaryDetails(diaryId = diaryId).toEntity()
    }
}