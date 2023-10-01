package com.signal.data.repository

import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.model.signin.SignInRequest
import com.signal.domain.repository.UserRepository

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
}