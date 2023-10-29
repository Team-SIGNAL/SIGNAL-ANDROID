package com.signal.signal_android.feature.signup

import androidx.lifecycle.viewModelScope
import com.signal.domain.enums.Gender
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.domain.regex.Regexs
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState.getDefaultState()) {

    private val passwordRegex = Regex(Regexs.PASSWORD)

    fun setName(name: String) {
        setState(
            state.value.copy(
                name = name,
                nameError = name.length !in 2..20,
            ),
        )
        setButtonEnabledUser()
    }

    fun setBirth(birth: LocalDate) {
        setState(state.value.copy(birth = birth))
    }

    fun setPhone(phone: String) {
        setState(state.value.copy(phone = phone))
        setButtonEnabledUser()
    }

    fun setGender(gender: Gender) {
        setState(state.value.copy(gender = gender))
    }

    fun setAccountId(accountId: String) {
        setState(
            state.value.copy(
                accountId = accountId,
                accountIdError = accountId.length !in 5..12,
            ),
        )
        setButtonEnabledAccount()
    }

    fun setPassword(password: String) {
        setState(
            state.value.copy(
                password = password,
                passwordError = !passwordRegex.matches(password),
            ),
        )
        setButtonEnabledAccount()
    }

    fun setRepeatPassword(repeatPassword: String) {
        with(state.value) {
            setState(
                copy(
                    repeatPassword = repeatPassword,
                    repeatPasswordError = repeatPassword != password,
                ),
            )
        }
        setButtonEnabledAccount()
    }

    fun checkValidate() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                postSideEffect(SignUpSideEffect.Success)
                setState(copy(buttonEnabled = false))
            }
        }
    }

    private fun setButtonEnabledUser() {
        with(state.value) {
            val isBlank = name.isBlank() || phone.isBlank()
            setState(copy(buttonEnabled = !isBlank && !nameError))
        }
    }

    private fun setButtonEnabledAccount() {
        with(state.value) {
            val isBlank = accountId.isBlank() || password.isBlank() || repeatPassword.isBlank()
            val isError = accountIdError || passwordError || repeatPasswordError
            setState(copy(buttonEnabled = !isBlank && !isError))
        }
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                signUpUseCase(
                    name = name,
                    birth = birth,
                    phone = phone,
                    accountId = accountId,
                    password = password,
                ).onSuccess {
                    postSideEffect(SignUpSideEffect.Success)
                }
            }
        }
    }
}
