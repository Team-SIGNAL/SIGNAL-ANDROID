package com.signal.data.datasource.user.local

import java.time.LocalDateTime

interface LocalUserDataSource {
    fun saveTokens(
        accessToken: String,
        refreshToken: String,
        expireAt: LocalDateTime,
    )

    fun getAccessToken(): String
    fun getRefreshToken(): String
    fun getExpireAt(): String
}