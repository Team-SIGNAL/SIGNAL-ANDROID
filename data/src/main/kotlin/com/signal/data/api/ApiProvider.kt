package com.signal.data.api

import com.signal.data.util.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    private val retrofit: Retrofit? = null

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private fun getRetrofit(
        tokenInterceptor: TokenInterceptor,
    ): Retrofit {
        return retrofit ?: Retrofit.Builder().baseUrl("http://44.202.99.157:8080").client(
            OkHttpClient.Builder().addInterceptor(tokenInterceptor)
                .addInterceptor(getLoggingInterceptor()).build()
        ).addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getUserApi(
        tokenInterceptor: TokenInterceptor,
    ): UserApi = getRetrofit(tokenInterceptor).create(UserApi::class.java)
}
