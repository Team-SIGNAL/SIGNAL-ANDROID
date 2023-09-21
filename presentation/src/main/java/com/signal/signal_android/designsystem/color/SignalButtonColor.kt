package com.signal.signal_android.designsystem.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

data class ButtonColor(
    val backgroundColor: BackgroundColor,
    val textColor: TextColor,
    val outlineColor: OutlineColor? = null,
)

data class BackgroundColor(
    val default: Color,
    val pressed: Color,
    val disabled: Color,
)

data class OutlineColor(
    val default: Color,
    val pressed: Color,
    val disabled: Color,
)

data class TextColor(
    val default: Color,
    val pressed: Color,
    val disabled: Color,
)

object SignalButtonColor {

    @Stable
    val Filled
        @Composable get() = ButtonColor(
            backgroundColor = BackgroundColor(
                default = SignalColor.Primary100,
                pressed = SignalColor.Primary300,
                disabled = SignalColor.Gray400,
            ),
            textColor = TextColor(
                default = SignalColor.Gray100,
                pressed = SignalColor.Gray100,
                disabled = SignalColor.Gray100,
            )
        )

    @Stable
    val Outlined
        @Composable get() = ButtonColor(
            backgroundColor = BackgroundColor(
                default = SignalColor.Transparent,
                pressed = SignalColor.Primary100,
                disabled = SignalColor.Transparent,
            ),
            textColor = TextColor(
                default = SignalColor.Primary100,
                pressed = SignalColor.Gray100,
                disabled = SignalColor.Gray400,
            ),
            outlineColor = OutlineColor(
                default = SignalColor.Primary100,
                pressed = SignalColor.Primary100,
                disabled = SignalColor.Gray400,
            )
        )
}