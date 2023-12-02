package com.signal.data.model.mypage

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("image") val image: String,
)