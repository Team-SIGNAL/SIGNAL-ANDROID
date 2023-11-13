package com.signal.data.api

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchAllDiaryResponse
import com.signal.data.model.diary.FetchDayDiaryResponse
import com.signal.data.model.diary.FetchMonthDiaryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DiaryApi {
    @POST(SignalUrl.Diary.CreateDiary)
    suspend fun createDiary(
        @Body createDiaryRequest: CreateDiaryRequest,
    )

    @GET(SignalUrl.Diary.FetchAllDiary)
    suspend fun fetchAllDiary(): FetchAllDiaryResponse

    @GET(SignalUrl.Diary.FetchMonthDiary)
    suspend fun fetchMonthDiary(
        @Query("date") date: String,
    ): FetchMonthDiaryResponse

    @GET(SignalUrl.Diary.FetchDayDiary)
    suspend fun fetchDayDiary(
        @Query("date") date: String,
    ): FetchDayDiaryResponse
}