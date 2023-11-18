package com.signal.data.datasource.user.local

import com.signal.data.model.mypage.FamousSayingModel

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
    fun clearUserInformation()

    fun saveAccountId(email: String)
    fun getAccountId(): String

    suspend fun addFamousSaying(famousSayings: List<FamousSayingModel>)
    suspend fun getFamousSaying(id: Long): FamousSayingModel?
}
