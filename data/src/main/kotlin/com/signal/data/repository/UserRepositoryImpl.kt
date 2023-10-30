package com.signal.data.repository

import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signup.SignUpRequest
import com.signal.domain.repository.UserRepository
import java.time.LocalDate

class UserRepositoryImpl(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {
    override suspend fun signIn(
        accountId: String,
        password: String,
    ) {
        remoteUserDataSource.signIn(
            request = SignInRequest(
                accountId = accountId,
                password = password,
            ),
        ).also {
            localUserDataSource.saveTokens(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken,
                expireAt = it.expireAt,
            )
        }
    }

    override suspend fun signUp(
        name: String,
        birth: LocalDate,
        phone: String,
        accountId: String,
        password: String,
    ) {
        remoteUserDataSource.signUp(
            SignUpRequest(
                name = name,
                birth = birth,
                phone = phone,
                accountId = accountId,
                password = password,
            ),
        )
    }

    override suspend fun secession() {
        remoteUserDataSource.secession()
    }

    override suspend fun signOut() {
        localUserDataSource.clearToken()
    }

    override suspend fun fetchUserInformation() {
        remoteUserDataSource.fetchUserInformation()
    }
}
