package com.signal.data.api

import com.signal.data.model.report.ReportBugRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi {
    @POST(SignalUrl.Report.ReportBug)
    suspend fun reportBug(
        @Body reportBugRequest: ReportBugRequest,
    )
}