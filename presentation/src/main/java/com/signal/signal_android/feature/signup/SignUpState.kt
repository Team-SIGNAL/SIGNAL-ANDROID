package com.signal.signal_android.feature.signup

import com.signal.domain.enums.Gender
import java.time.LocalDate

data class SignUpState(
    val name: String,
    val birth: LocalDate?,
    val phone: String,
    val gender: Gender,
    val accountId: String,
    val password: String,
    val repeatPassword: String,
) {
    companion object {
        fun getDefaultState() = SignUpState(
            name = "",
            birth = null,
            phone = "",
            accountId = "",
            gender = Gender.MALE,
            password = "",
            repeatPassword = "",
        )
    }
}