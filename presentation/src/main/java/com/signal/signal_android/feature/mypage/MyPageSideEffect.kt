package com.signal.signal_android.feature.mypage

sealed class MyPageSideEffect {
    object Success : MyPageSideEffect()
    object ServerError : MyPageSideEffect()
    object SignOutSuccess : MyPageSideEffect()
}