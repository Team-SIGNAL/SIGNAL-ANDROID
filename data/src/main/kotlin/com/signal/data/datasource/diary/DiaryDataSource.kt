package com.signal.data.datasource.diary

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchDiariesResponse
import com.signal.data.model.diary.FetchDiaryDetailsResponse
import com.signal.data.model.diary.FetchMonthDiariesResponse

interface DiaryDataSource {
    suspend fun createDiary(createDiaryRequest: CreateDiaryRequest)
    suspend fun fetchAllDiaries(): FetchDiariesResponse
    suspend fun fetchMonthDiaries(date: String): FetchMonthDiariesResponse
    suspend fun fetchDayDiaries(date: String): FetchDiariesResponse
    suspend fun fetchDiaryDetails(diaryId: Long): FetchDiaryDetailsResponse
}