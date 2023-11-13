package com.signal.data.datasource.diary

import com.signal.data.api.DiaryApi
import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchAllDiaryResponse
import com.signal.data.model.diary.FetchDayDiaryResponse
import com.signal.data.model.diary.FetchMonthDiaryResponse
import com.signal.data.util.ExceptionHandler

class DiaryDateSourceImpl(
    private val diaryApi: DiaryApi,
) : DiaryDataSource {
    override suspend fun createDiary(createDiaryRequest: CreateDiaryRequest) =
        ExceptionHandler<Unit>().httpRequest {
            diaryApi.createDiary(createDiaryRequest = createDiaryRequest)
        }.sendRequest()

    override suspend fun fetchAllDiary(): FetchAllDiaryResponse =
        ExceptionHandler<FetchAllDiaryResponse>().httpRequest {
            diaryApi.fetchAllDiary()
        }.sendRequest()

    override suspend fun fetchMonthDiary(date: String): FetchMonthDiaryResponse =
        ExceptionHandler<FetchMonthDiaryResponse>().httpRequest {
            diaryApi.fetchMonthDiary(date = date)
        }.sendRequest()

    override suspend fun fetchDayDiary(date: String): FetchDayDiaryResponse =
        ExceptionHandler<FetchDayDiaryResponse>().httpRequest {
            diaryApi.fetchDayDiary(date = date)
        }.sendRequest()

}