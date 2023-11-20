package com.signal.data.datasource.diary

import com.signal.data.api.DiaryApi
import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.model.diary.FetchDiariesResponse
import com.signal.data.model.diary.FetchDiaryDetailsResponse
import com.signal.data.model.diary.FetchMonthDiariesResponse
import com.signal.data.util.ExceptionHandler
import java.util.UUID

class DiaryDataSourceImpl(
    private val diaryApi: DiaryApi,
) : DiaryDataSource {
    override suspend fun createDiary(createDiaryRequest: CreateDiaryRequest) =
        ExceptionHandler<Unit>().httpRequest {
            diaryApi.createDiary(createDiaryRequest = createDiaryRequest)
        }.sendRequest()

    override suspend fun fetchAllDiaries() = ExceptionHandler<FetchDiariesResponse>().httpRequest {
        diaryApi.fetchAllDiaries()
    }.sendRequest()

    override suspend fun fetchMonthDiaries(date: String) =
        ExceptionHandler<FetchMonthDiariesResponse>().httpRequest {
            diaryApi.fetchMonthDiaries(date = date)
        }.sendRequest()

    override suspend fun fetchDayDiaries(date: String) =
        ExceptionHandler<FetchDiariesResponse>().httpRequest {
            diaryApi.fetchDayDiaries(date = date)
        }.sendRequest()

    override suspend fun fetchDiaryDetails(diaryId: UUID) =
        ExceptionHandler<FetchDiaryDetailsResponse>().httpRequest {
            diaryApi.fetchDiaryDetails(diaryId = diaryId)
        }.sendRequest()

    override suspend fun deleteDiary(diaryId: UUID) = ExceptionHandler<Unit>().httpRequest {
        diaryApi.deleteDiary(diaryId = diaryId)
    }.sendRequest()

}