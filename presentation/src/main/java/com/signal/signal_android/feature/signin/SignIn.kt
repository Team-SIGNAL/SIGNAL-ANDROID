package com.signal.signal_android.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun SignIn(
    moveToSignUp: () -> Unit,
) {

    // TODO 더미값 제거 -> 서버 로직 연동 시
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val onIdChange = { value: String ->
        id = value
    }

    val onPasswordChange = { value: String ->
        password = value
    }

    val onSignInClick: () -> Unit = {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(76.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_signal_logo_sign_in),
            contentDescription = stringResource(id = R.string.app_logo),
        )
        Spacer(modifier = Modifier.height(46.dp))
        SignInInputs(
            id = id,
            onIdChange = onIdChange,
            password = password,
            onPasswordChange = onPasswordChange,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Body(text = stringResource(id = R.string.sign_in_no_account))
            Body(
                modifier = Modifier.signalClickable(onClick = moveToSignUp),
                text = stringResource(id = R.string.sign_in_do_sign_up),
                color = SignalColor.Primary100,
                decoration = TextDecoration.Underline,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SignalFilledButton(
            text = stringResource(id = R.string.sign_in),
            onClick = onSignInClick,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SignInInputs(
    id: String,
    onIdChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    SignalTextField(
        value = id,
        onValueChange = onIdChange,
        hint = stringResource(id = R.string.sign_in_hint_id),
        title = stringResource(id = R.string.id),
        description = stringResource(id = R.string.sign_in_description_id),
    )
    Spacer(modifier = Modifier.height(20.dp))
    SignalTextField(
        value = password,
        onValueChange = onPasswordChange,
        hint = stringResource(id = R.string.sign_in_hint_password),
        title = stringResource(id = R.string.password),
        description = stringResource(id = R.string.sign_in_description_password),
    )
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    SignIn {

    }
}