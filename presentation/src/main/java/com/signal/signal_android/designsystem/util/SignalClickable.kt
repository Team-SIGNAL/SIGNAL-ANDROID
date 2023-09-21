package com.signal.signal_android.designsystem.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

@SuppressLint("ComposableModifierFactory")
@Stable
@Composable
fun Modifier.signalClickable(
    rippleEnabled: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) = composed {
    Modifier.clickable(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        indication = rememberRipple(
            color = Color.Unspecified,
        ).takeIf {
            rippleEnabled
        },
    )
}