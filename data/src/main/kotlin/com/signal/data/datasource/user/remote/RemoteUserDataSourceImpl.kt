package com.signal.data.datasource.user.remote

import com.signal.data.api.UserApi
import com.signal.data.model.mypage.EditProfileRequest
import com.signal.data.model.mypage.FetchUserInformationResponse
import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signin.SignInResponse
import com.signal.data.model.signup.SignUpRequest
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
                ),
            )
        }.sendRequest()

    override suspend fun signUp(request: SignUpRequest) = ExceptionHandler<Unit>().httpRequest {
        userApi.signUp(signUpRequest = request)
    }.sendRequest()

    override suspend fun secession() = ExceptionHandler<Unit>().httpRequest {
        userApi.secession()
    }.sendRequest()

    override suspend fun fetchUserInformation() =
        ExceptionHandler<FetchUserInformationResponse>().httpRequest {
            userApi.fetchUserInformation()
        }.sendRequest()

    override suspend fun editProfile(editProfileRequest: EditProfileRequest) =
        ExceptionHandler<Unit>().httpRequest {
            userApi.editProfile(editProfileRequest = editProfileRequest)
        }.sendRequest()
}