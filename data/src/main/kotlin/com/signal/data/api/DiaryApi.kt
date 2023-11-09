package com.signal.data.api

import com.signal.data.model.diary.CreateDiaryRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryApi {
    @POST(SignalUrl.Diary.CreateDiary)
    suspend fun createDiary(
        @Body createDiaryRequest: CreateDiaryRequest,
    )
}