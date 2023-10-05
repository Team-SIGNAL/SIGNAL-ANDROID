package com.signal.signal_android.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.signal.domain.enums.Gender
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.SignUpTitle
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.radiobutton.SignalRadioButton
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpUser(
    moveToSignIn: () -> Unit,
    moveToSignUpAccount: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val state by signUpViewModel.state.collectAsState()

    val onNextButtonClick = { }

    val datePickerState = rememberDatePickerState()

    var isShowDialog by remember { mutableStateOf(false) }

    val birth = SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis)

    LaunchedEffect(birth) {
        signUpViewModel.setBirth(birth.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SignUpTitle()
        SignUpInputs(
            datePickerState = { datePickerState },
            isShowDialog = { isShowDialog },
            showDialog = { isShowDialog = !isShowDialog },
            name = { state.name },
            birth = { state.birth.toString() },
            phoneNumber = { state.phone },
            gender = { state.gender },
            onNameChange = signUpViewModel::setName,
            onPhoneNumberChange = signUpViewModel::setPhone,
            onGenderChange = signUpViewModel::setGender,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpInputs(
    datePickerState: () -> DatePickerState,
    isShowDialog: () -> Boolean,
    showDialog: () -> Unit,
    name: () -> String,
    birth: () -> String,
    phoneNumber: () -> String,
    gender: () -> Gender,
    onNameChange: (String) -> Unit,
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
    if (isShowDialog()) {
        DatePickerDialog(
            onDismissRequest = showDialog,
            confirmButton = {},
            content = { DatePicker(state = datePickerState()) })
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        BodyLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.birth),
            color = SignalColor.Gray500,
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SignalColor.Gray600,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyLarge(text = birth())
        Image(
            modifier = Modifier.signalClickable(onClick = showDialog),
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = stringResource(id = R.string.text_field_icon),
        )
    }
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
    SignUpUser(
        moveToSignIn = { },
        moveToSignUpAccount = {},
        signUpViewModel = viewModel(),
    )
}