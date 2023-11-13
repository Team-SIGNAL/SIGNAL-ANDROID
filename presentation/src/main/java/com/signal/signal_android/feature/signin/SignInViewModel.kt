package com.signal.signal_android.feature.signin

import androidx.lifecycle.viewModelScope
import com.signal.domain.exception.NotFoundException
import com.signal.domain.exception.OfflineException
import com.signal.domain.exception.UnAuthorizationException
import com.signal.domain.usecase.users.SaveAccountIdUseCase
import com.signal.domain.usecase.users.SignInUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val saveAccountIdUseCase: SaveAccountIdUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>(SignInState.getDefaultState()) {

    internal fun setAccountId(accountId: String) {
        setState(state.value.copy(accountId = accountId))
        setAccountIdError(false)
        setButtonEnabled()
    }

    internal fun setPassword(password: String) {
        setState(state.value.copy(password = password))
        setPasswordError(false)
        setButtonEnabled()
    }

    internal fun setAutoSignIn(autoSignIn: Boolean) {
        setState(state.value.copy(autoSignIn = autoSignIn))
    }

    private fun setButtonEnabled() {
        with(state.value) {
            val isBlank = accountId.isBlank() || password.isBlank()
            val isError = accountIdError || passwordError
            setState(copy(buttonEnabled = !isBlank && !isError))
        }
    }

    private fun setAccountIdError(error: Boolean) {
        setState(state.value.copy(accountIdError = error))
    }

    private fun setPasswordError(error: Boolean) {
        setState(state.value.copy(passwordError = error))
    }

    internal fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            signInUseCase(
                accountId = state.value.accountId,
                password = state.value.password,
            ).onSuccess {
                saveAccountId()
                postSideEffect(SignInSideEffect.Success)
            }.onFailure {
                when (it) {
                    is UnAuthorizationException -> setPasswordError(true)
                    is NotFoundException -> setAccountIdError(true)
                    is OfflineException -> postSideEffect(SignInSideEffect.CheckInternetConnection)
                    else -> postSideEffect(SignInSideEffect.ServerError)
                }
            }
        }
    }

    private fun saveAccountId(){
        saveAccountIdUseCase(email = state.value.accountId)
    }
}
