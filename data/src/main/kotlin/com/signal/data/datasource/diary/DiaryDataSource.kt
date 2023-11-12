package com.signal.data.datasource.diary

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchAllDiaryResponse
import com.signal.data.model.diary.FetchDayDiaryResponse
import com.signal.data.model.diary.FetchMonthDiaryResponse
import java.time.LocalDateTime

interface DiaryDataSource {
    suspend fun createDiary(createDiaryRequest: CreateDiaryRequest)
    suspend fun fetchAllDiary(): FetchAllDiaryResponse
    suspend fun fetchMonthDiary(date: LocalDateTime): FetchMonthDiaryResponse
    suspend fun fetchDayDiary(date: LocalDateTime): FetchDayDiaryResponse
}