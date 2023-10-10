package com.signal.domain.repository

import java.time.LocalDate

interface UserRepository {
    suspend fun signIn(
        accountId: String,
        password: String,
    )

    suspend fun signUp(
        name: String,
        birth: LocalDate,
        phone: String,
        accountId: String,
        password: String,
    )
}
