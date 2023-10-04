package com.signal.domain.repository

interface UserRepository {
    suspend fun signIn(
        accountId: String,
        password: String,
    )
}