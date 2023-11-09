package com.signal.data.datasource.diary

import com.signal.data.api.DiaryApi
import com.signal.data.model.diary.CreateDiaryRequest
import com.signal.data.util.ExceptionHandler

class DiaryDateSourceImpl(
    private val diaryApi: DiaryApi,
) : DiaryDataSource {
    override suspend fun createDiary(createDiaryRequest: CreateDiaryRequest) =
        ExceptionHandler<Unit>().httpRequest {
            diaryApi.createDiary(createDiaryRequest)
        }.sendRequest()
}