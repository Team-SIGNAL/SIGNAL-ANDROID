package com.signal.signal_android.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.SignalColor

@Composable
internal fun SignalDialog(
    title: String,
    onCancelBtnClick: () -> Unit,
    onCheckBtnClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(SignalColor.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Body2(
            text = title,
            color = SignalColor.Black,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.4.dp)
                .padding(horizontal = 12.dp),
            color = SignalColor.Gray500,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .clickable(
                        onClick = onCancelBtnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body2(
                    text = stringResource(id = R.string.my_page_secession_cancel),
                    color = SignalColor.Gray500,
                )
            }
            Divider(
                modifier = Modifier
                    .width(0.4.dp)
                    .height(40.dp)
                    .padding(vertical = 4.dp),
            )
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .clickable(
                        onClick = onCheckBtnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body2(
                    text = stringResource(id = R.string.my_page_secession_check),
                    color = SignalColor.Error,
                )
            }
        }
    }
}
