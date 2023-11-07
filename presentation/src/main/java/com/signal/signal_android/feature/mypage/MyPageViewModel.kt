package com.signal.signal_android.feature.mypage

import androidx.lifecycle.viewModelScope
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    val signOutUseCase: SignOutUseCase,
    val secessionUseCase: SecessionUseCase,
    val fetchUserInformationUseCase: FetchUserInformationUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState.getDefaultState()) {

    init {
        fetchUserInformation()
    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SignOutSuccess)
            }
        }
    }

    internal fun secession() {
        viewModelScope.launch(Dispatchers.IO) {
            secessionUseCase().onFailure {
                when (it) {
                    is KotlinNullPointerException -> {
                        postSideEffect(MyPageSideEffect.SecessionSuccess)
                    }
                }
            }
        }
    }

    internal fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUserInformationUseCase().onSuccess {
                setState(state.value.copy())
            }.onFailure {

            }
        }
    }
}