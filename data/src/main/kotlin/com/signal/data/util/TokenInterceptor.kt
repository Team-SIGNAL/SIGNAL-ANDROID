package com.signal.data.util

import com.signal.data.api.SignalUrl
import com.signal.data.datasource.user.local.LocalUserDataSource
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val localUserDataSource: LocalUserDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val ignorePaths = listOf(
            SignalUrl.Users.SignIn,
        )

        if (ignorePaths.contains(path)) {
            return chain.proceed(request)
        }

        return chain.proceed(
            request
                .newBuilder()
                .header("Authorization", "Bearer ${localUserDataSource.getAccessToken()}")
                .build(),
        )
    }
}
