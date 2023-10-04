package com.signal.data.api

import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST(SignalUrl.Users.SignIn)
    fun signIn(
        @Body signInRequest: SignInRequest,
    ): SignInResponse
}