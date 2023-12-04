package com.signal.data.datasource.report

import com.signal.data.api.ReportApi
import com.signal.data.model.report.ReportBugRequest
import com.signal.data.util.ExceptionHandler

class ReportDataSourceImpl(
    private val reportApi: ReportApi,
) : ReportDataSource {
    override suspend fun reportBug(reportBugRequest: ReportBugRequest) =
        ExceptionHandler<Unit>().httpRequest {
            reportApi.reportBug(reportBugRequest = reportBugRequest)
        }.sendRequest()
}