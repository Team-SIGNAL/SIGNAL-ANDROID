package com.signal.signal_android.feature.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.dropdown.SignalDropDown
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField

@Composable
internal fun Report(
    moveToBack: () -> Unit,
) {
    var selected: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = stringResource(id = R.string.report_header_title),
            onClick = moveToBack,
        )
        PostInformation(
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png",
            title = "인스타그램",
            date = "2023-10-28",
            writer = "정승훈",
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box {
            Box(modifier = Modifier.padding(top = 80.dp)) {
                SignalTextField(
                    modifier = Modifier.fillMaxHeight(0.7f),
                    value = "",
                    onValueChange = {},
                    hint = stringResource(id = R.string.report_content),
                    title = stringResource(id = R.string.report_content_title),
                    showLength = true,
                    alignment = Alignment.Top,
                )
            }
            SignalDropDown(
                title = stringResource(id = R.string.report_reason),
                hint = stringResource(id = R.string.report_hint_reason),
                items = listOf("부적절한 게시글", "김은오 바보"),
                selected = selected,
                onItemSelected = {
                    selected = it
                },
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = {},
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun PostInformation(
    imageUrl: String,
    title: String,
    writer: String,
    date: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SignalColor.Gray100)
            .border(
                shape = RoundedCornerShape(8.dp),
                width = 1.dp,
                color = SignalColor.Primary100,
            )
            .padding(
                vertical = 16.dp,
                horizontal = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
            model = imageUrl,
            contentDescription = stringResource(id = R.string.feed_image),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BodyStrong(text = title)
            Body(text = writer)
        }
    }
}
