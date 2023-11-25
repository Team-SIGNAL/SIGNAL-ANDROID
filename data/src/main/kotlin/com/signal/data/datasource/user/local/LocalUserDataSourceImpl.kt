package com.signal.data.datasource.user.local

import com.signal.data.database.SignalDatabase
import com.signal.data.model.mypage.FamousSayingModel
import com.signal.data.model.mypage.UserInformationModel
import com.signal.data.util.PreferenceManager

class LocalUserDataSourceImpl(
    private val database: SignalDatabase,
    private val preferenceManager: PreferenceManager,
) : LocalUserDataSource {
    private val myPageDao = database.getMyPageDao()

    override fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessExpiredAt: String,
        refreshExpiredAt: String,
    ) {
        preferenceManager.getSharedPreferenceEditor().also {
            it.putString(Keys.ACCESS_TOKEN, accessToken)
            it.putString(Keys.REFRESH_TOKEN, refreshToken)
            it.putString(Keys.ACCESS_EXPIRED_AT, accessExpiredAt)
            it.putString(Keys.REFRESH_EXPIRED_AT, refreshExpiredAt)
        }.apply()
    }

    override fun getAccessToken(): String {
        return preferenceManager.getSharedPreference().getString(Keys.ACCESS_TOKEN, "") ?: ""
    }

    override fun getRefreshToken(): String {
        return preferenceManager.getSharedPreference().getString(Keys.REFRESH_TOKEN, "") ?: ""
    }

    override fun getExpireAt(): String {
        return preferenceManager.getSharedPreference().getString(Keys.ACCESS_EXPIRED_AT, "") ?: ""
    }

    override fun clearUserInformation() {
        preferenceManager.getSharedPreferenceEditor().apply {
            remove(Keys.ACCESS_TOKEN)
            remove(Keys.REFRESH_TOKEN)
            remove(Keys.REFRESH_TOKEN)
            remove(Keys.REFRESH_EXPIRED_AT)
            remove(Keys.ACCOUNT_ID)
        }.apply()
    }

    override fun saveAccountId(email: String) {
        preferenceManager.getSharedPreferenceEditor().putString(Keys.ACCOUNT_ID, email).apply()
    }

    override fun getAccountId() =
        preferenceManager.getSharedPreference().getString(Keys.ACCOUNT_ID, "") ?: ""

    override suspend fun addFamousSaying(famousSayings: List<FamousSayingModel>) {
        myPageDao.addFamousSaying(famousSayings)
    }

    override suspend fun getFamousSaying(id: Long) = myPageDao.getFamousSaying(id = id)

    override suspend fun setUserInformation(userInformationModel: UserInformationModel) {
        myPageDao.setUserInformation(userInformationModel = userInformationModel)
    }

    override suspend fun updateUserInformation(userInformationModel: UserInformationModel) {
        myPageDao.updateUserInformation(userInformationModel = userInformationModel)
    }

    override suspend fun getUserInformation() =
        myPageDao.getUserInformation()
}

object Keys {
    const val NAME = "signal"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val ACCESS_EXPIRED_AT = "access_expired_at"
    const val REFRESH_EXPIRED_AT = "refresh_expired_at"
    const val LAST_DIAGNOSIS_DATE = "last_diagnosis_date"
    const val ACCOUNT_ID = "account_id"
}
