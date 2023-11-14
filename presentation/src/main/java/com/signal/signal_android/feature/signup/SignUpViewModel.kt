package com.signal.signal_android.feature.signup

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.viewModelScope
import com.signal.domain.enums.Gender
import com.signal.domain.usecase.users.SignUpUseCase
import com.signal.signal_android.BaseViewModel
import com.signal.signal_android.domain.regex.Regexs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun setBirth(birth: String) {
        setState(state.value.copy(birth = birth))
    }

    fun setPhone(phone: String) {
        setState(
            state.value.copy(
                phone = phone,
                phoneError = phone.length != 11,
            )
        )
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
        postSideEffect(SignUpSideEffect.Success)
        setState(state.value.copy(buttonEnabled = false))
    }

    private fun setButtonEnabledUser() {
        with(state.value) {
            val isError = nameError || phoneError
            val isBlank = name.isBlank() || phone.isBlank()
            setState(copy(buttonEnabled = !isBlank && !isError))
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
                PhoneNumberUtils.formatNumber(phone, "")
                signUpUseCase(
                    name = name,
                    birth = birth,
                    phone = phone.toPhoneNumber(),
                    accountId = accountId,
                    password = password,
                    gender = gender,
                ).onSuccess {
                    postSideEffect(SignUpSideEffect.Success)
                }
            }
        }
    }
}

private fun String.toPhoneNumber() = StringBuilder().apply {
    with(this@toPhoneNumber) {
        append(substring(0..2))
        append('-')
        append(substring(3..6))
        append('-')
        append(substring(7..10))
    }
}.toString()
