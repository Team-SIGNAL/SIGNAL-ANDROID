package com.signal.data.api

import com.signal.data.model.attachment.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {
    @POST(SignalUrl.Attachment.Upload)
    suspend fun fileUpload(
        @Part file: List<MultipartBody.Part>,
    ): UploadFileResponse
}