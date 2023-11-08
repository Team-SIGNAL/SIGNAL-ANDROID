package com.signal.domain.repository

import com.signal.domain.entity.UploadFileEntity
import java.io.File

interface FileRepository {
    suspend fun uploadFile(files: List<File>): Result<UploadFileEntity>
}