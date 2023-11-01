package com.signal.data.datasource.user.local

interface LocalUserDataSource {
    fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessExpiredAt: String,
        refreshExpiredAt: String,
    )

    fun getAccessToken(): String
    fun getRefreshToken(): String
    fun getExpireAt(): String
    fun clearToken()
}
