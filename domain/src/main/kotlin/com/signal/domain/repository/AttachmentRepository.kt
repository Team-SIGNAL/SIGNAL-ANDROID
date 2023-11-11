package com.signal.domain.repository

import com.signal.domain.entity.UploadFileEntity
import java.io.File

interface AttachmentRepository {
    suspend fun uploadFile(image: File): Result<UploadFileEntity>
}