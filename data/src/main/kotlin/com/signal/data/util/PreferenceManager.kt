package com.signal.data.util

import android.content.Context
import android.content.SharedPreferences
import com.signal.data.datasource.user.local.Keys

class PreferenceManager(
    private val context: Context,
) {
    internal fun getSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(Keys.NAME, Context.MODE_PRIVATE)
    }

    internal fun getSharedPreferenceEditor(): SharedPreferences.Editor {
        return getSharedPreference().edit()
    }
}
