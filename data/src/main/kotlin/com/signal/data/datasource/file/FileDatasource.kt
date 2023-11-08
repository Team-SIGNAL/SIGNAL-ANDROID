package com.signal.data.datasource.file

import com.signal.data.model.attachment.UploadFileResponse
import okhttp3.MultipartBody

interface FileDatasource {
    suspend fun uploadFile(file: List<MultipartBody.Part>): UploadFileResponse
}