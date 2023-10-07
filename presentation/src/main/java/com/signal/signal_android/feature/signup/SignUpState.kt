package com.signal.signal_android.feature.signup

import com.signal.domain.enums.Gender
import java.time.LocalDate

data class SignUpState(
    val name: String,
    val nameError: Boolean,
    val birth: LocalDate,
    val phone: String,
    val gender: Gender,
    val accountId: String,
    val accountIdError: Boolean,
    val password: String,
    val passwordError: Boolean,
    val repeatPassword: String,
    val repeatPasswordError: Boolean,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = SignUpState(
            name = "",
            nameError = false,
            birth = LocalDate.now(),
            phone = "",
            accountId = "",
            accountIdError = false,
            gender = Gender.MALE,
            password = "",
            passwordError = false,
            repeatPassword = "",
            repeatPasswordError = false,
            buttonEnabled = false,
        )
    }
}