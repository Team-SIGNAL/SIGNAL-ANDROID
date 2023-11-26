package com.signal.data.datasource.user.local

import com.signal.data.model.mypage.FamousSayingModel
import com.signal.data.model.mypage.UserInformationModel

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

    suspend fun setUserInformation(userInformationModel: UserInformationModel)
    suspend fun updateUserInformation(userInformationModel: UserInformationModel)
    suspend fun getUserInformation(): UserInformationModel
}
