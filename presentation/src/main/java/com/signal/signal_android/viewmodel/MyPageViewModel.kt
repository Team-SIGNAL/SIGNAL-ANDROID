package com.signal.signal_android.viewmodel

import androidx.lifecycle.viewModelScope
import com.signal.domain.exception.BadRequestException
import com.signal.domain.exception.ForbiddenException
import com.signal.domain.exception.NotFoundException
import com.signal.domain.exception.UnAuthorizationException
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.feature.mypage.MyPageSideEffect
import com.signal.signal_android.feature.mypage.MyPageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val secessionUseCase: SecessionUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState.getDefaultState()) {

    internal fun secession() {
        viewModelScope.launch(Dispatchers.IO) {
            secessionUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.Success)
            }.onFailure {
                when (it) {
                    is BadRequestException -> ""
                    is UnAuthorizationException -> ""
                    is ForbiddenException -> ""
                    is NotFoundException -> ""
                    else -> postSideEffect(MyPageSideEffect.ServerError)
                }
            }
        }
    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SignOutSuccess)
            }
        }
    }
}