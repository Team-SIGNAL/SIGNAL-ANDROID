package com.signal.data.repository

import com.signal.data.datasource.user.local.LocalUserDataSource
import com.signal.data.datasource.user.remote.RemoteUserDataSource
import com.signal.data.model.mypage.toEntity
import com.signal.data.model.mypage.toModel
import com.signal.data.model.signin.SignInRequest
import com.signal.data.model.signup.SignUpRequest
import com.signal.domain.entity.FamousSayingEntity
import com.signal.domain.entity.UserInformationEntity
import com.signal.domain.enums.Gender
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
                accessExpiredAt = it.accessExpiredAt,
                refreshExpiredAt = it.refreshExpiredAt,
            )
        }
    }

    override suspend fun signUp(
        name: String,
        birth: String,
        phone: String,
        accountId: String,
        password: String,
        gender: Gender,
    ) {
        remoteUserDataSource.signUp(
            SignUpRequest(
                name = name,
                birth = birth,
                phone = phone,
                accountId = accountId,
                password = password,
                gender = gender,
            ),
        )
    }

    override suspend fun secession() {
        remoteUserDataSource.secession()
    }

    override suspend fun signOut() {
        localUserDataSource.clearUserInformation()
    }

    override suspend fun fetchUserInformation(): UserInformationEntity =
        remoteUserDataSource.fetchUserInformation().toEntity()

    override fun saveAccountId(email: String) {
        localUserDataSource.saveAccountId(email = email)
    }

    override fun getAccountId() = localUserDataSource.getAccountId()

    override suspend fun addFamousSaying(famousSayings: List<FamousSayingEntity>) {
        localUserDataSource.addFamousSaying(famousSayings = famousSayings.map { it.toModel() })
    }

    override suspend fun getFamousSaying(id: Long) =
        localUserDataSource.getFamousSaying(id = id)?.toEntity()

}
