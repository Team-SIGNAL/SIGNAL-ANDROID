package com.signal.signal_android.feature.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun CreatePost(
    moveToBack: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = stringResource(id = R.string.create_post_header_title),
            onClick = moveToBack,
        )
        Spacer(modifier = Modifier.height(4.dp))
        SignalTextField(
            value = title,
            onValueChange = {
                title = it
            },
            hint = stringResource(id = R.string.create_post_title_hint),
            title = stringResource(id = R.string.create_post_title),
            showLength = true,
            maxLength = 20,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SignalTextField(
            modifier = Modifier.fillMaxHeight(0.5f),
            value = content,
            onValueChange = {
                content = it
            },
            hint = stringResource(id = R.string.create_post_content_hint),
            title = stringResource(id = R.string.create_post_content),
            alignment = Alignment.Top,
            showLength = true,
            singleLine = false,
            maxLength = 100,
        )
        PostImage(onClick = {})
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = { /*TODO*/ },
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun PostImage(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 28.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(88.dp)
            .background(SignalColor.Gray300)
            .signalClickable(
                onClick = onClick,
                rippleEnabled = true,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = stringResource(id = R.string.create_post_image),
            tint = SignalColor.Gray500,
        )
    }
}
