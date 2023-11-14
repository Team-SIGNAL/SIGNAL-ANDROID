package com.signal.data.datasource.diary

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchDiariesResponse
import com.signal.data.model.diary.FetchDiaryDetailsResponse
import com.signal.data.model.diary.FetchMonthDiariesResponse

interface DiaryDataSource {
    suspend fun createDiary(createDiaryRequest: CreateDiaryRequest)
    suspend fun fetchAllDiary(): FetchDiariesResponse
    suspend fun fetchMonthDiary(date: String): FetchMonthDiariesResponse
    suspend fun fetchDayDiary(date: String): FetchDiariesResponse
    suspend fun fetchDiaryDetails(diaryId: Long): FetchDiaryDetailsResponse
}