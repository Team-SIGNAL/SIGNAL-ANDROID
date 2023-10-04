package com.signal.data.datasource.user.remote

import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse

interface RemoteUserDataSource {
    suspend fun signIn(request: SignInRequest): SignInResponse
}