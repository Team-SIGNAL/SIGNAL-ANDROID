package com.signal.signal_android.feature.mypage

import androidx.lifecycle.viewModelScope
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    val signOutUseCase: SignOutUseCase,
    val secessionUseCase: SecessionUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState.getDefaultState()) {

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SignOutSuccess)
            }
        }
    }

    internal fun secession() {
        viewModelScope.launch(Dispatchers.IO) {
            secessionUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SecessionSuccess)
            }
        }
    }
}