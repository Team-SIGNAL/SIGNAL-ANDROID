package com.signal.data.datasource.user.remote

import com.signal.data.model.mypage.FetchUserInformationResponse
import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse
import com.signal.data.model.signup.SignUpRequest

interface RemoteUserDataSource {
    suspend fun signIn(request: SignInRequest): SignInResponse
    suspend fun signUp(request: SignUpRequest)
    suspend fun secession()
    suspend fun fetchUserInformation(): FetchUserInformationResponse
}
