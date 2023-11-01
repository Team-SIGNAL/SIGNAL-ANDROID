package com.signal.data.model.signin

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_expired_at") val accessExpiredAt: String,
    @SerializedName("refresh_expired_at") val refreshExpiredAt: String,
)
