package com.signal.signal_android.viewmodel

import androidx.lifecycle.viewModelScope
import com.signal.domain.exception.NotFoundException
import com.signal.domain.exception.OfflineException
import com.signal.domain.exception.UnAuthorizationException
import com.signal.domain.usecase.SignInUseCase
import com.signal.signal_android.feature.signin.SignInSideEffect
import com.signal.signal_android.feature.signin.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>(SignInState.getDefaultState()) {

    internal fun setAccountId(accountId: String) {
        setState(state.value.copy(accountId = accountId))
    }

    internal fun setPassword(password: String) {
        setState(state.value.copy(password = password))
    }

    internal fun setAutoSignIn(autoSignIn: Boolean) {
        setState(state.value.copy(autoSignIn = autoSignIn))
    }

    internal fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            signInUseCase(
                accountId = state.value.accountId,
                password = state.value.password,
            ).onSuccess {
                postSideEffect(SignInSideEffect.Success)
            }.onFailure {
                postSideEffect(
                    when (it) {
                        is UnAuthorizationException -> SignInSideEffect.MismatchPassword
                        is NotFoundException -> SignInSideEffect.NotFound
                        is OfflineException -> SignInSideEffect.CheckInternetConnection
                        else -> SignInSideEffect.ServerError
                    }
                )
            }
        }
    }
}