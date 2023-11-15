package com.signal.signal_android.feature.main.mypage

sealed class MyPageSideEffect {
    object SecessionSuccess : MyPageSideEffect()
    object SignOutSuccess : MyPageSideEffect()
}