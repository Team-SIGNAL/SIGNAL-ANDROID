package com.signal.data.repository

import com.signal.data.datasource.file.AttachmentDataSource
import com.signal.data.model.attachment.toEntity
import com.signal.domain.repository.AttachmentRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AttachmentRepositoryImpl(private val attachmentDataSource: AttachmentDataSource) : AttachmentRepository {
    override suspend fun uploadFile(files: List<File>) = kotlin.runCatching {
        attachmentDataSource.uploadFile(file = files.map { it.getImageMultipart(key = "file") })
            .toEntity()
    }
}

private fun File.getImageMultipart(key: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = key,
        filename = name,
        body = asRequestBody("multipart/form-data".toMediaType()),
    )