package com.signal.data.api

import com.signal.data.util.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    private val retrofit: Retrofit? = null

    private fun getRetrofit(
        tokenInterceptor: TokenInterceptor,
    ): Retrofit {
        return retrofit
            ?: Retrofit.Builder()
                .baseUrl("https://test.com")
                .client(OkHttpClient.Builder().addInterceptor(tokenInterceptor).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getUserApi(
        tokenInterceptor: TokenInterceptor,
    ): UserApi = getRetrofit(tokenInterceptor).create(UserApi::class.java)
}
