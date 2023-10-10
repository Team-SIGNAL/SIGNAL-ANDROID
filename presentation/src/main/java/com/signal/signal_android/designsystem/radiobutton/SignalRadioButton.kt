package com.signal.signal_android.designsystem.radiobutton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun SignalRadioButton(
    value: Boolean,
    onValueChange: () -> Unit,
    enabled: Boolean = true,
) {
    val color by animateColorAsState(
        targetValue = if (!enabled) {
            SignalColor.Gray400
        } else if (value) {
            SignalColor.Primary100
        } else {
            SignalColor.Gray500
        },
        label = stringResource(id = R.string.radio_button_color),
    )

    val alpha by animateFloatAsState(
        targetValue = if (value) {
            1f
        } else {
            0f
        },
        label = stringResource(id = R.string.radio_button_alpha),
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .signalClickable(
                rippleEnabled = true,
                onClick = onValueChange,
            )
            .background(
                color = color,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(
                    color = SignalColor.Gray100,
                    shape = CircleShape,
                ),
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .alpha(alpha)
                .clip(CircleShape)
                .background(
                    color = color,
                    shape = CircleShape,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignalRadioButtonPreview() {
    SignalRadioButton(
        value = true,
        onValueChange = {},
    )
}
