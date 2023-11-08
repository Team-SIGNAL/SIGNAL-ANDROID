package com.signal.data.repository

import com.signal.data.datasource.file.FileDatasource
import com.signal.data.model.attachment.toEntity
import com.signal.domain.repository.FileRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileRepositoryImpl(private val fileDatasource: FileDatasource) : FileRepository {
    override suspend fun uploadFile(files: List<File>) = kotlin.runCatching {
        fileDatasource.uploadFile(file = files.map { it.getImageMultipart(key = "file") })
            .toEntity()
    }
}

private fun File.getImageMultipart(key: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = key,
        filename = name,
        body = asRequestBody("multipart/form-data".toMediaType()),
    )