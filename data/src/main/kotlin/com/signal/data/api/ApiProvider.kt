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
        return retrofit ?: Retrofit.Builder().baseUrl("http://54.152.35.68:8080/").client(
            OkHttpClient.Builder().addInterceptor(tokenInterceptor)
                .addInterceptor(getLoggingInterceptor()).build(),
        ).addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getUserApi(tokenInterceptor: TokenInterceptor): UserApi {
        return getRetrofit(tokenInterceptor).create(UserApi::class.java)
    }

    fun getFeedApi(tokenInterceptor: TokenInterceptor): FeedApi {
        return getRetrofit(tokenInterceptor).create(FeedApi::class.java)
    }

    fun getFileApi(
        tokenInterceptor: TokenInterceptor,
    ): AttachmentApi = getRetrofit(tokenInterceptor).create(AttachmentApi::class.java)

    fun getDiaryApi(tokenInterceptor: TokenInterceptor): DiaryApi {
        return getRetrofit(tokenInterceptor).create(DiaryApi::class.java)
    }
}
