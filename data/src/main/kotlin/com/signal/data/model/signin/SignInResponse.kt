package com.signal.data.model.signin

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expire_at") val expireAt: LocalDateTime,
)