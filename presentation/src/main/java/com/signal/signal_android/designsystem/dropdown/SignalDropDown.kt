package com.signal.signal_android.designsystem.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun SignalDropDown(
    title: String,
    hint: String,
    selected: String? = null,
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotated by animateFloatAsState(
        targetValue = if (expanded) {
            180f
        } else {
            0f
        },
        label = "",
    )

    Column {
        Body(
            text = title,
            color = SignalColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .signalClickable(rippleEnabled = true, onClick = { expanded = !expanded })
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = SignalColor.Gray500,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BodyLarge(
                text = selected ?: hint,
                color = if (selected != null) {
                    SignalColor.Error
                } else {
                    SignalColor.Gray400
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.rotate(rotated),
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = stringResource(id = R.string.dropdown_icon),
                tint = SignalColor.Gray500,
            )
        }
        AnimatedVisibility(
            modifier = Modifier.padding(top = 6.dp),
            visible = expanded,
        ) {
            DropDownList(items = items, onItemSelected = {
                expanded = false
                onItemSelected(it)
            })
        }
    }
}

@Composable
private fun DropDownList(
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = SignalColor.White)
            .border(
                width = 1.dp,
                color = SignalColor.Gray500,
                shape = RoundedCornerShape(8.dp),
            ),
    ) {
        items.forEachIndexed { index, element ->
            Body2(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SignalColor.White)
                    .signalClickable(
                        rippleEnabled = true,
                        onClick = { onItemSelected(element) },
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                text = element,
                color = SignalColor.Gray600,
            )
            if (index != items.lastIndex) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = SignalColor.Gray400,
                )
            }
        }
    }
}
