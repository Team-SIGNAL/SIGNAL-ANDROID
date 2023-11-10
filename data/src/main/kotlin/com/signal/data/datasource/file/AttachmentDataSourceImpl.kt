package com.signal.data.datasource.file

import com.signal.data.api.AttachmentApi
import com.signal.data.model.attachment.UploadFileResponse
import com.signal.data.util.ExceptionHandler
import okhttp3.MultipartBody

class AttachmentDataSourceImpl(private val attachmentApi: AttachmentApi) : AttachmentDataSource {
    override suspend fun uploadFile(file: List<MultipartBody.Part>) =
        ExceptionHandler<UploadFileResponse>().httpRequest {
            attachmentApi.fileUpload(file = file)
        }.sendRequest()
}