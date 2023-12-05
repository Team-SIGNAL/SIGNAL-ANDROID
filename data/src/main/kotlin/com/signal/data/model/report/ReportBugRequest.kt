package com.signal.data.model.report

import com.google.gson.annotations.SerializedName

class ReportBugRequest(
    @SerializedName("content") val content: String,
    @SerializedName("image") val image: String?,
)