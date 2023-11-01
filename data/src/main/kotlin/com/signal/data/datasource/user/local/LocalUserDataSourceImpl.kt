package com.signal.data.datasource.user.local

import android.content.Context
import android.content.SharedPreferences

class LocalUserDataSourceImpl(
    private val context: Context,
) : LocalUserDataSource {
    override fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessExpiredAt: String,
        refreshExpiredAt: String,
    ) {
        getSharedPreferenceEditor().also {
            it.putString(Keys.ACCESS_TOKEN, accessToken)
            it.putString(Keys.REFRESH_TOKEN, refreshToken)
            it.putString(Keys.ACCESS_EXPIRED_AT, accessExpiredAt)
            it.putString(Keys.REFRESH_EXPIRED_AT, refreshExpiredAt)
        }.apply()
    }

    override fun getAccessToken(): String {
        return getSharedPreference().getString(Keys.ACCESS_TOKEN, "") ?: ""
    }

    override fun getRefreshToken(): String {
        return getSharedPreference().getString(Keys.REFRESH_TOKEN, "") ?: ""
    }

    override fun getExpireAt(): String {
        return getSharedPreference().getString(Keys.ACCESS_EXPIRED_AT, "") ?: ""
    }

    override fun clearToken() {
        getSharedPreference().edit().clear().apply()
    }

    private fun getSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(Keys.NAME, Context.MODE_PRIVATE)
    }

    private fun getSharedPreferenceEditor(): SharedPreferences.Editor {
        return getSharedPreference().edit()
    }
}

object Keys {
    const val NAME = "signal"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val ACCESS_EXPIRED_AT = "access_expired_at"
    const val REFRESH_EXPIRED_AT = "refresh_expired_at"
}
