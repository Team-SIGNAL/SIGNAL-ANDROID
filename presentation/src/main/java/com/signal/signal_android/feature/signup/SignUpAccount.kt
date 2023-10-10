package com.signal.signal_android.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.SignUpTitle
import com.signal.signal_android.designsystem.textfield.SignalTextField

@Composable
internal fun SignUpAccount(
    moveToSignIn: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {
    val state by signUpViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffect.collect {
            when (it) {
                is SignUpSideEffect.Success -> moveToSignIn()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SignUpTitle()
        SignUpInputs(
            accountId = { state.accountId },
            accountIdError = { state.accountIdError },
            password = { state.password },
            passwordError = { state.passwordError },
            repeatPassword = { state.repeatPassword },
            repeatPasswordError = { state.repeatPasswordError },
            onIdChange = signUpViewModel::setAccountId,
            onPasswordChange = signUpViewModel::setPassword,
            onConfirmPasswordChange = signUpViewModel::setRepeatPassword,
        )
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.sign_up),
            onClick = signUpViewModel::signUp,
            enabled = state.buttonEnabled,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SignUpInputs(
    accountId: () -> String,
    accountIdError: () -> Boolean,
    password: () -> String,
    passwordError: () -> Boolean,
    repeatPassword: () -> String,
    repeatPasswordError: () -> Boolean,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SignalTextField(
            value = accountId(),
            onValueChange = onIdChange,
            hint = stringResource(id = R.string.sign_up_hint_id),
            title = stringResource(id = R.string.id),
            error = accountIdError(),
            description = stringResource(id = R.string.sign_up_account_id_error),
        )
        SignalTextField(
            value = password(),
            onValueChange = onPasswordChange,
            hint = stringResource(id = R.string.sign_up_hint_password),
            title = stringResource(id = R.string.password),
            isPassword = true,
            error = passwordError(),
            description = stringResource(id = R.string.sign_up_password_error),
        )
        SignalTextField(
            value = repeatPassword(),
            onValueChange = onConfirmPasswordChange,
            hint = stringResource(id = R.string.sign_up_hint_password_confirm),
            title = stringResource(id = R.string.password_confirm),
            isPassword = true,
            error = repeatPasswordError(),
            description = stringResource(id = R.string.sign_up_repeat_password_error),
        )
    }
}
