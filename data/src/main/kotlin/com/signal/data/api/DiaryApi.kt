package com.signal.data.api

import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchDiariesResponse
import com.signal.data.model.diary.FetchDiaryDetailsResponse
import com.signal.data.model.diary.FetchMonthDiariesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DiaryApi {
    @POST(SignalUrl.Diary.CreateDiary)
    suspend fun createDiary(
        @Body createDiaryRequest: CreateDiaryRequest,
    )

    @GET(SignalUrl.Diary.FetchAllDiaries)
    suspend fun fetchAllDiaries(): FetchDiariesResponse

    @GET(SignalUrl.Diary.FetchMonthDiaries)
    suspend fun fetchMonthDiaries(
        @Query("date") date: String,
    ): FetchMonthDiariesResponse

    @GET(SignalUrl.Diary.CreateDiary)
    suspend fun fetchDayDiaries(
        @Query("date") date: String,
    ): FetchDiariesResponse

    @GET(SignalUrl.Diary.CreateDiary)
    suspend fun fetchDiaryDetails(
        @Query("diary_id") diaryId: Long,
    ): FetchDiaryDetailsResponse
}