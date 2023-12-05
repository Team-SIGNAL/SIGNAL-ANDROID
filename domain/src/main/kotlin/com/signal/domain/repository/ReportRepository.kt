package com.signal.domain.repository

interface ReportRepository {
    suspend fun reportBug(
        content: String,
        image: String?,
    ): Result<Unit>
}