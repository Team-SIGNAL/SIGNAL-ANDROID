package com.signal.signal_android.feature.signup

import androidx.lifecycle.viewModelScope
import com.signal.domain.enums.Gender
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState.getDefaultState()) {

    fun setName(name: String) {
        setState(state.value.copy(name = name))
    }

    fun setBirth(birth: String) {
        setState(state.value.copy(birth = LocalDate.parse(birth)))
    }

    fun setPhone(phone: String) {
        setState(state.value.copy(phone = phone))
    }

    fun setGender(gender: Gender) {

    }

    fun setAccountId(accountId: String) {
        setState(state.value.copy(accountId = accountId))
    }

    fun setPassword(password: String) {
        setState(state.value.copy(password = password))
    }

    fun setRepeatPassword(repeatPassword: String) {
        setState(state.value.copy(repeatPassword = repeatPassword))
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                signUpUseCase(
                    name = name,
                    birth = birth!!,
                    phone = phone,
                    accountId = accountId,
                    password = password,
                ).onSuccess {
                    postSideEffect(SignUpSideEffect.Success)
                }.onFailure {

                }
            }
        }
    }

}