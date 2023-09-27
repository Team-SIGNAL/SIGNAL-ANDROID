package com.signal.signal_android.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.domain.enums.Gender
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.Title
import com.signal.signal_android.designsystem.radiobutton.SignalRadioButton
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun SignUpUser(
    moveToSignIn: () -> Unit,
) {

    // TODO viewmodel state 사용
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }

    val onNameChange: (String) -> Unit = { name = it }
    val onBirthChange: (String) -> Unit = { birth = it }
    val onPhoneNumberChange: (String) -> Unit = { phoneNumber = it }
    val onGenderChange: (Gender) -> Unit = { gender = it }

    val onNextButtonClick = { }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(66.dp))
        Title(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            text = stringResource(id = R.string.sign_up),
        )
        Spacer(modifier = Modifier.height(60.dp))
        SignUpInputs(
            name = { name },
            birth = { birth },
            phoneNumber = { phoneNumber },
            gender = { gender },
            onNameChange = onNameChange,
            onBirthChange = onBirthChange,
            onPhoneNumberChange = onPhoneNumberChange,
            onGenderChange = onGenderChange,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Body(text = stringResource(id = R.string.sign_up_have_account))
            Spacer(modifier = Modifier.width(8.dp))
            Body(
                modifier = Modifier.signalClickable(onClick = moveToSignIn),
                text = stringResource(id = R.string.sign_up_sign_in),
                decoration = TextDecoration.Underline,
                color = SignalColor.Primary100,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SignalFilledButton(
            text = stringResource(id = R.string.next),
            onClick = onNextButtonClick,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SignUpInputs(
    name: () -> String,
    birth: () -> String,
    phoneNumber: () -> String,
    gender: () -> Gender,
    onNameChange: (String) -> Unit,
    onBirthChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
) {
    SignalTextField(
        value = name(),
        onValueChange = onNameChange,
        hint = stringResource(id = R.string.sign_up_hint_name),
        title = stringResource(id = R.string.name),
    )
    Spacer(modifier = Modifier.height(14.dp))
    SignalTextField(
        value = birth(),
        onValueChange = onBirthChange,
        hint = stringResource(id = R.string.sign_up_hint_birth),
        title = stringResource(id = R.string.birth),
    )
    Spacer(modifier = Modifier.height(14.dp))
    SignalTextField(
        value = phoneNumber(),
        onValueChange = onPhoneNumberChange,
        hint = stringResource(id = R.string.sign_up_hint_phone_number),
        title = stringResource(id = R.string.phone_number),
    )
    Spacer(modifier = Modifier.height(14.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        BodyLarge(
            text = stringResource(id = R.string.gender),
            color = SignalColor.Gray500,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SignalRadioButton(
                value = (gender() == Gender.MALE),
                onValueChange = { onGenderChange(Gender.MALE) },
            )
            Spacer(modifier = Modifier.width(6.dp))
            BodyLarge(text = stringResource(id = R.string.male))
            Spacer(modifier = Modifier.width(22.dp))
            SignalRadioButton(
                value = (gender() == Gender.FEMALE),
                onValueChange = { onGenderChange(Gender.FEMALE) },
            )
            Spacer(modifier = Modifier.width(6.dp))
            BodyLarge(text = stringResource(id = R.string.female))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpUserPreview() {
    SignUpUser {

    }
}