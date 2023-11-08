package com.signal.data.datasource.file

import com.signal.data.api.FileApi
import com.signal.data.model.attachment.UploadFileResponse
import com.signal.data.util.ExceptionHandler
import okhttp3.MultipartBody

class FileDataSourceImpl(private val fileApi: FileApi) : FileDatasource {
    override suspend fun uploadFile(file: List<MultipartBody.Part>) =
        ExceptionHandler<UploadFileResponse>().httpRequest {
            fileApi.fileUpload(file = file)
        }.sendRequest()
}