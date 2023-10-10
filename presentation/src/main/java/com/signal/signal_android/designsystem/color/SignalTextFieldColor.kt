package com.signal.signal_android.designsystem.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.signal.signal_android.designsystem.foundation.SignalColor

data class TextFieldColor(
    val titleColor: TitleColor,
    val outlineColor: TextFieldOutlineColor,
    val backgroundColor: TextFieldBackgroundColor,
    val descriptionColor: DescriptionColor,
)

data class TitleColor(
    val default: Color,
    val disabled: Color,
)

data class TextFieldOutlineColor(
    val default: Color,
    val disabled: Color,
    val focused: Color,
)

data class TextFieldBackgroundColor(
    val default: Color,
    val disabled: Color,
)

data class DescriptionColor(
    val default: Color,
    val disabled: Color,
)

object SignalTextFieldColor {

    @Stable
    val Default
        @Composable get() = TextFieldColor(
            titleColor = TitleColor(
                default = SignalColor.Gray500,
                disabled = SignalColor.Gray400,
            ),
            outlineColor = TextFieldOutlineColor(
                default = SignalColor.Gray500,
                disabled = SignalColor.Gray400,
                focused = SignalColor.Primary100,
            ),
            backgroundColor = TextFieldBackgroundColor(
                default = SignalColor.Transparent,
                disabled = SignalColor.Gray200,
            ),
            descriptionColor = DescriptionColor(
                default = SignalColor.Gray400,
                disabled = SignalColor.Gray400,
            ),
        )
}
