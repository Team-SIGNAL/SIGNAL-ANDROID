package com.signal.signal_android.feature.signin

sealed class SignInSideEffect {
    object Success : SignInSideEffect()
    object NotFound : SignInSideEffect()
    object MismatchPassword : SignInSideEffect()
    object CheckInternetConnection : SignInSideEffect()
    object ServerError : SignInSideEffect()
}