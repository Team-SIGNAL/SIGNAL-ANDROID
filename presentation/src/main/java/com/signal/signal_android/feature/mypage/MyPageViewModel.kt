package com.signal.signal_android.feature.mypage

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val secessionUseCase: SecessionUseCase,
    initialState: MyPageState,
) : BaseViewModel<MyPageState, MyPageSideEffect>(initialState) {

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
                postSideEffect(MyPageSideEffect.Success)
            }
        }
    }
}