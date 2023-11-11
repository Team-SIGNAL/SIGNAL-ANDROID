package com.signal.data.model.attachment

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.UploadFileEntity

data class UploadFileResponse(
    @SerializedName("image") val image: String,
)

fun UploadFileResponse.toEntity() = UploadFileEntity(
    image = this.image,
)
