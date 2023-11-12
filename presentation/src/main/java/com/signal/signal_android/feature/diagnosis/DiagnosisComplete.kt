package com.signal.signal_android.feature.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.Title
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DiagnosisComplete(
    moveToMain: () -> Unit,
    diagnosisViewModel: DiagnosisViewModel = koinViewModel(),
) {
    val requiredTime = "13분"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        // TODO: 이미지 변경
        Image(
            modifier = Modifier.size(132.dp),
            painter = painterResource(id = R.drawable.ic_landing_diary),
            contentDescription = stringResource(id = R.string.diagnosis_complete_image),
        )
        Spacer(modifier = Modifier.height(42.dp))
        DiagnosisTexts(requiredTime = requiredTime)
        Spacer(modifier = Modifier.weight(1f))
        Buttons(
            onMainButtonClicked = {},
            onSubButtonClicked = {
                moveToMain()
                diagnosisViewModel.saveLastDiagnosisDate()
            },
            mainText = stringResource(id = R.string.diagnosis_complete_check_result),
            subText = stringResource(id = R.string.diagnosis_complete_move_to_main),
        )
    }
}

@Composable
private fun DiagnosisTexts(requiredTime: String) {
    Title(
        modifier = Modifier.padding(bottom = 2.dp),
        text = stringResource(id = R.string.diagnosis_complete_title),
    )
    BodyStrong(
        modifier = Modifier.padding(bottom = 2.dp),
        text = stringResource(id = R.string.diagnosis_description),
        color = SignalColor.Primary100,
    )
    Body(
        modifier = Modifier.padding(bottom = 76.dp),
        text = "걸린 시간 : $requiredTime",
        color = SignalColor.Gray500,
    )
}
