package com.signal.signal_android.designsystem.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.designsystem.color.SignalTextFieldColor
import com.signal.signal_android.designsystem.color.TextFieldColor
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor

@Stable
private val TextFieldShape = RoundedCornerShape(8.dp)

@Composable
internal fun SignalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String? = null,
    description: String? = null,
    hint: String,
    isPassword: Boolean = false,
    error: Boolean = false,
    color: TextFieldColor = SignalTextFieldColor.Default,
    enabled: Boolean = true,
) {

    var isFocused by remember { mutableStateOf(false) }

    val titleColor = if (!enabled) color.titleColor.disabled
    else if (error) SignalColor.Error
    else color.titleColor.default

    val borderColor = if (!enabled) color.outlineColor.disabled
    else if (error) SignalColor.Error
    else if (isFocused) color.outlineColor.focused
    else color.outlineColor.default

    Column {
        if (title != null) {
            BodyLarge(
                text = title,
                color = titleColor,
            )
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(TextFieldShape)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = TextFieldShape,
                )
                .background(
                    color = if (!enabled) color.backgroundColor.disabled
                    else color.backgroundColor.default,
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            value = value,
            onValueChange = onValueChange,
        ) {
            Row(
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 18.dp,
                )
            ) {
                BodyLarge(
                    text = hint,
                    color = if (!enabled) SignalColor.Gray300
                    else SignalColor.Gray400,
                )
            }
        }
        if (description != null) {
            AnimatedVisibility(error) {
                Column(
                    modifier = Modifier.padding(
                        top = 4.dp,
                        start = 8.dp,
                    )
                ) {
                    Body(
                        text = description,
                        color = if (!enabled) color.descriptionColor.disabled
                        else if (error) SignalColor.Error
                        else color.descriptionColor.default
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignalTextFieldPreview() {
    SignalTextField(
        value = "",
        onValueChange = {},
        hint = "hint",
        title = "title",
        description = "description",
        error = true,
        enabled = false,
    )
}