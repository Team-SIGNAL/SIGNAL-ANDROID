package com.signal.signal_android.feature.signup

import com.signal.domain.enums.Gender

data class SignUpState(
    val name: String,
    val nameError: Boolean,
    val birth: String,
    val phone: String,
    val phoneError: Boolean,
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
            birth = "",
            phone = "",
            phoneError = false,
            accountId = "",
            accountIdError = false,
            gender = Gender.MAN,
            password = "",
            passwordError = false,
            repeatPassword = "",
            repeatPasswordError = false,
            buttonEnabled = false,
        )
    }
}
