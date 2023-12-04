package com.signal.data.repository

import com.signal.data.datasource.report.ReportDataSource
import com.signal.data.model.report.ReportBugRequest
import com.signal.domain.repository.ReportRepository

class ReportRepositoryImpl(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override suspend fun reportBug(
        content: String,
        image: String?,
    ) = kotlin.runCatching {
        reportDataSource.reportBug(
            reportBugRequest = ReportBugRequest(
                content = content,
                image = image,
            ),
        )
    }
}