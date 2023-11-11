package com.signal.data.datasource.file

import com.signal.data.model.attachment.UploadFileResponse
import okhttp3.MultipartBody

interface AttachmentDataSource {
    suspend fun uploadFile(image: MultipartBody.Part): UploadFileResponse
}