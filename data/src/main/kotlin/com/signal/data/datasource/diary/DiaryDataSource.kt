package com.signal.data.datasource.diary

import com.signal.data.model.diary.CreateDiaryRequest

interface DiaryDataSource {
    suspend fun createDiary(createDiaryRequest: CreateDiaryRequest)
}