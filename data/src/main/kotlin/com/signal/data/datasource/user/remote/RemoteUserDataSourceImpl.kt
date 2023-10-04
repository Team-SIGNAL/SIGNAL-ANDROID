package com.signal.data.datasource.user.remote

import com.signal.data.api.UserApi
import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse
import com.signal.data.util.ExceptionHandler

class RemoteUserDataSourceImpl(
    private val userApi: UserApi,
) : RemoteUserDataSource {
    override suspend fun signIn(request: SignInRequest) =
        ExceptionHandler<SignInResponse>().httpRequest {
            userApi.signIn(
                signInRequest = SignInRequest(
                    accountId = request.accountId,
                    password = request.password,
                )
            )
        }.sendRequest()
}