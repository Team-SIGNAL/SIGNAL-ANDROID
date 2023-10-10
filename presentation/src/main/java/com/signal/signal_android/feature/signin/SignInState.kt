package com.signal.signal_android.feature.signin

data class SignInState(
    val accountId: String,
    val password: String,
    val autoSignIn: Boolean,
    val accountIdError: Boolean,
    val passwordError: Boolean,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = SignInState(
            accountId = "",
            password = "",
            autoSignIn = false,
            accountIdError = false,
            passwordError = false,
            buttonEnabled = false,
        )
    }
}
