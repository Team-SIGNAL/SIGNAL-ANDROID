package com.signal.signal_android.feature.mypage

import com.signal.signal_android.feature.signin.SignInSideEffect

sealed class MyPageSideEffect {
    object Success : MyPageSideEffect()
}