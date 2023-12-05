package com.signal.data.datasource.report

import com.signal.data.model.report.ReportBugRequest

interface ReportDataSource {
    suspend fun reportBug(reportBugRequest: ReportBugRequest)
}