package com.signal.signal_android.designsystem.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.color.SignalTextFieldColor
import com.signal.signal_android.designsystem.color.TextFieldColor
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable

@Stable
private val TextFieldShape = RoundedCornerShape(8.dp)

@Composable
internal fun SignalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String? = null,
    description: String? = null,
    hint: String,
    singleLine: Boolean = true,
    isPassword: Boolean = false,
    error: Boolean = false,
    color: TextFieldColor = SignalTextFieldColor.Default,
    enabled: Boolean = true,
) {

    var isFocused by remember { mutableStateOf(false) }

    var isVisible by remember { mutableStateOf(false) }

    val onVisibleChange = {
        isVisible = !isVisible
    }

    val titleColor = if (!enabled) color.titleColor.disabled
    else if (error) SignalColor.Error
    else color.titleColor.default

    val borderColor by animateColorAsState(
        targetValue = if (!enabled) color.outlineColor.disabled
        else if (error) SignalColor.Error
        else if (isFocused) color.outlineColor.focused
        else color.outlineColor.default,
        label = stringResource(id = R.string.text_field_border_color),
    )

    Column {
        if (title != null) {
            BodyLarge(
                text = title,
                color = titleColor,
            )
            Spacer(modifier = Modifier.height(4.dp))
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
            singleLine = singleLine,
            visualTransformation = if (isPassword && isVisible) PasswordVisualTransformation()
            else VisualTransformation.None,
        ) { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        )
                        .weight(1f)
                ) {
                    if (value.isEmpty()) {
                        BodyLarge(
                            text = hint,
                            color = if (!enabled) SignalColor.Gray300
                            else SignalColor.Gray400,
                        )
                    }
                    innerTextField()
                }
                if (isPassword) {
                    Image(
                        modifier = Modifier.signalClickable(onClick = onVisibleChange),
                        painter = painterResource(
                            id = if (isVisible) R.drawable.ic_visible_off
                            else R.drawable.ic_visible_on,
                        ),
                        contentDescription = stringResource(id = R.string.text_field_icon),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
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
                        text = description, color = if (!enabled) color.descriptionColor.disabled
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
private fun SignalTextFieldPreview() {
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