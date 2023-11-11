package com.signal.data.api

import com.signal.data.model.attachment.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AttachmentApi {
    @Multipart
    @POST(SignalUrl.Attachment.Upload)
    suspend fun fileUpload(
        @Part image: MultipartBody.Part,
    ): UploadFileResponse
}