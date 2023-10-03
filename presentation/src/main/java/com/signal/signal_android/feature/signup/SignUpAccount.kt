package com.signal.signal_android.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.SignUpTitle
import com.signal.signal_android.designsystem.textfield.SignalTextField

@Composable
internal fun SignUpAccount(
    moveToSignIn: () -> Unit,
) {

    // TODO viewModel state 사용
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val onIdChange: (String) -> Unit = { id = it }
    val onPasswordChange: (String) -> Unit = { password = it }
    val onConfirmPasswordChange: (String) -> Unit = { confirmPassword = it }

    val onSignUpClick: () -> Unit = {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SignUpTitle()
        SignUpInputs(
            id = { id },
            password = { password },
            confirmPassword = { confirmPassword },
            onIdChange = onIdChange,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
        )
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.sign_up),
            onClick = onSignUpClick,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SignUpInputs(
    id: () -> String,
    password: () -> String,
    confirmPassword: () -> String,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SignalTextField(
            value = id(),
            onValueChange = onIdChange,
            hint = stringResource(id = R.string.sign_up_hint_id),
            title = stringResource(id = R.string.id),
        )
        SignalTextField(
            value = password(),
            onValueChange = onPasswordChange,
            hint = stringResource(id = R.string.sign_up_hint_password),
            title = stringResource(id = R.string.password),
        )
        SignalTextField(
            value = confirmPassword(),
            onValueChange = onConfirmPasswordChange,
            hint = stringResource(id = R.string.sign_up_hint_password_confirm),
            title = stringResource(id = R.string.password_confirm),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    SignUpAccount {

    }
}