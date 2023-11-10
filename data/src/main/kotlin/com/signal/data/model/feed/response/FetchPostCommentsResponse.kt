package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class FetchPostCommentsResponse(
    @SerializedName("comment") val comments: List<Comment>,
) {
    data class Comment(
        @SerializedName("writer") val writer: String,
        @SerializedName("content") val content: String,
        @SerializedName("is_mine") val isMine: Boolean,
        @SerializedName("date_time") val dateTime: LocalDateTime,
    )
}
