package com.signal.data.datasource.user.local

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDateTime

class LocalUserDataSourceImpl(
    private val context: Context,
) : LocalUserDataSource {

    override fun saveTokens(
        accessToken: String,
        refreshToken: String,
        expireAt: LocalDateTime,
    ) {
        getSharedPreferenceEditor().also {
            it.putString(Keys.ACCESS_TOKEN, accessToken)
            it.putString(Keys.REFRESH_TOKEN, refreshToken)
            it.putString(Keys.EXPIRE_AT, expireAt.toString())
        }.apply()
    }

    override fun getAccessToken(): String {
        return getSharedPreference().getString(Keys.ACCESS_TOKEN, "") ?: ""
    }

    override fun getRefreshToken(): String {
        return getSharedPreference().getString(Keys.REFRESH_TOKEN, "") ?: ""
    }

    override fun getExpireAt(): String {
        return getSharedPreference().getString(Keys.EXPIRE_AT, "") ?: ""
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
    const val EXPIRE_AT = "expire_at"
}
