package com.signal.domain.repository

import com.signal.domain.entity.FamousSayingEntity
import com.signal.domain.entity.UserInformationEntity
import com.signal.domain.enums.Gender

interface UserRepository {
    suspend fun signIn(
        accountId: String,
        password: String,
    )

    suspend fun signUp(
        name: String,
        birth: String,
        phone: String,
        accountId: String,
        password: String,
        gender: Gender,
    )

    suspend fun secession()
    suspend fun signOut()
    suspend fun fetchUserInformation(): UserInformationEntity
    fun saveAccountId(email: String)
    fun getAccountId(): String

    suspend fun addFamousSaying(famousSayings: List<FamousSayingEntity>)
    suspend fun getFamousSaying(id: Long): FamousSayingEntity?
}
