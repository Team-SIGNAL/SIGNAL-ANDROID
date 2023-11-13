package com.signal.data.datasource.diary

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchAllDiaryResponse
import com.signal.data.model.diary.FetchDayDiaryResponse
import com.signal.data.model.diary.FetchMonthDiaryResponse

interface DiaryDataSource {
    suspend fun createDiary(createDiaryRequest: CreateDiaryRequest)
    suspend fun fetchAllDiary(): FetchAllDiaryResponse
    suspend fun fetchMonthDiary(date: String): FetchMonthDiaryResponse
    suspend fun fetchDayDiary(date: String): FetchDayDiaryResponse
}