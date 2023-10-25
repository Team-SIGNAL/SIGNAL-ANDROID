package com.signal.signal_android.feature.mypage

sealed class MyPageSideEffect {
    object SecessionSuccess : MyPageSideEffect()
    object SignOutSuccess : MyPageSideEffect()
}