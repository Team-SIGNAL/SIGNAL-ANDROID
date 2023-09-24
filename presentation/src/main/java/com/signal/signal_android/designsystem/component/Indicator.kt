package com.signal.signal_android.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.SignalColor

private const val DURATION = 500

@Composable
fun Indicator(isEnabled: Boolean) {

    val size by animateDpAsState(
        targetValue = if (isEnabled) 9.dp
        else 7.dp,
        label = stringResource(id = R.string.indicator_width),
        animationSpec = tween(DURATION)
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) SignalColor.Primary100
        else SignalColor.Gray100,
        label = stringResource(id = R.string.indicator_background_color),
        animationSpec = tween(DURATION)
    )

    Box(
        modifier = Modifier
            .size(size = size)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = SignalColor.Primary100,
            )
            .background(
                color = backgroundColor,
                shape = CircleShape,
            )
            .clip(CircleShape)
    )
}