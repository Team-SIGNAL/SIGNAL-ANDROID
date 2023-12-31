package com.signal.signal_android.feature.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.button.SignalOutlinedButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.Title

@Composable
internal fun DiagnosisLanding(
    moveToDiagnosis: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Introduce()
        Spacer(modifier = Modifier.weight(1f))
        Buttons(
            onMainButtonClicked = moveToDiagnosis,
            onSubButtonClicked = {},
            mainText = stringResource(id = R.string.diagnosis_do_diagnosis),
            subText = stringResource(id = R.string.diagnosis_check_histories),
            subVisibility = { false },
        )
    }
}

@Composable
private fun Introduce() {
    Image(
        modifier = Modifier.padding(bottom = 54.dp),
        painter = painterResource(id = R.drawable.ic_landing_self_diagnosis),
        contentDescription = stringResource(id = R.string.diagnosis_image),
    )
    Title(
        modifier = Modifier.padding(bottom = 4.dp),
        text = stringResource(id = R.string.diagnosis_title),
    )
    BodyStrong(
        modifier = Modifier.padding(bottom = 4.dp),
        text = stringResource(id = R.string.diagnosis_description),
        color = SignalColor.Primary100,
    )
    Body(
        text = stringResource(id = R.string.diagnosis_time),
        color = SignalColor.Gray500,
    )
}

@Composable
internal fun Buttons(
    onMainButtonClicked: () -> Unit,
    onSubButtonClicked: () -> Unit,
    mainText: String,
    subText: String,
    mainEnabled: () -> Boolean? = { null },
    subEnabled: () -> Boolean? = { null },
    mainVisibility: () -> Boolean = { true },
    subVisibility: () -> Boolean = { true },
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 32.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        if (mainVisibility()) {
            SignalFilledButton(
                text = mainText,
                onClick = onMainButtonClicked,
                enabled = mainEnabled() ?: true,
            )
        }
        if (subVisibility()) {
            SignalOutlinedButton(
                text = subText,
                onClick = onSubButtonClicked,
                enabled = subEnabled() ?: true,
            )
        }
    }
}
