package com.signal.signal_android.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.designsystem.color.ButtonColor
import com.signal.signal_android.designsystem.color.SignalButtonColor
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.util.signalClickable

@Stable
private val ButtonShape = RoundedCornerShape(8.dp)

@Composable
private fun BasicButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    color: ButtonColor,
    enabled: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()

    val textColor = if (!enabled) {
        color.textColor.disabled
    } else if (isPressed) {
        color.textColor.pressed
    } else {
        color.textColor.default
    }

    val backgroundColor = if (!enabled) {
        color.backgroundColor.disabled
    } else if (isPressed) {
        color.backgroundColor.pressed
    } else {
        color.backgroundColor.default
    }

    val outlineColor: Color? = if (!enabled) {
        color.outlineColor?.disabled
    } else if (isPressed) {
        color.outlineColor?.pressed
    } else {
        color.outlineColor?.default
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = outlineColor ?: backgroundColor,
                shape = ButtonShape,
            )
            .signalClickable(
                rippleEnabled = false,
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = onClick,
            )
            .background(
                color = backgroundColor,
                shape = ButtonShape,
            )
            .clip(ButtonShape)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BodyStrong(
            text = text,
            color = textColor,
        )
    }
}

@Composable
internal fun SignalFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: ButtonColor = SignalButtonColor.Filled,
    enabled: Boolean = true,
) {
    BasicButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        color = color,
        enabled = enabled,
    )
}

@Composable
internal fun SignalOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: ButtonColor = SignalButtonColor.Outlined,
    enabled: Boolean = true,
) {
    BasicButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        color = color,
        enabled = enabled,
    )
}

@Preview(
    showBackground = true,
    name = "Filled Button",
)
@Composable
fun FilledButtonPreview() {
    SignalFilledButton(text = "버튼", onClick = { /*TODO*/ })
}

@Preview(
    showBackground = true,
    name = "Outlined Button",
)
@Composable
fun OutlinedButtonPreview() {
    SignalOutlinedButton(text = "버튼", onClick = { /*TODO*/ })
}
