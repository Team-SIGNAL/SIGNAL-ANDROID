package com.signal.data.repository

import com.signal.data.datasource.diary.DiaryDataSource
import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.toEntity
import com.signal.domain.entity.FetchAllDiaryEntity
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.entity.FetchMonthDiaryEntity
import com.signal.domain.enums.Emotion
import com.signal.domain.repository.DiaryRepository

class DiaryRepositoryImpl(
    private val diaryDateSource: DiaryDataSource,
) : DiaryRepository {
    override suspend fun fetchAllDiary(): FetchAllDiaryEntity =
        diaryDateSource.fetchAllDiary().toEntity()

    override suspend fun fetchMonthDiary(
        date: String
    ): FetchMonthDiaryEntity = diaryDateSource.fetchMonthDiary(
        date = date
    ).toEntity()

    override suspend fun fetchDayDiary(
        date: String
    ): FetchDayDiaryEntity = diaryDateSource.fetchDayDiary(
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
}