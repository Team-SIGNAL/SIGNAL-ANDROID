package com.signal.data.api

import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse
import com.signal.data.model.signup.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface UserApi {
    @POST(SignalUrl.Users.SignIn)
    fun signIn(
        @Body signInRequest: SignInRequest,
    ): SignInResponse

    @POST(SignalUrl.Users.SignUp)
    fun signUp(
        @Body signUpRequest: SignUpRequest,
    )

    @DELETE(SignalUrl.Users.Secession)
    fun secession()
}
