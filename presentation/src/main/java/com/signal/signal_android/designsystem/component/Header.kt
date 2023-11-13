package com.signal.signal_android.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.SubTitle

@Composable
internal fun Header(
    title: String,
    leadingIcon: Painter? = painterResource(id = R.drawable.ic_back),
    trailingIcon: Painter? = null,
    onLeadingClicked: (() -> Unit)? = null,
    onTrailingClicked: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.run {
                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = onLeadingClicked!!,
                ) {
                    Icon(
                        painter = this,
                        contentDescription = stringResource(id = R.string.header_back),
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            SubTitle(text = title)
        }
        trailingIcon?.run {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = onTrailingClicked!!,
            ) {
                Icon(
                    painter = this,
                    contentDescription = stringResource(id = R.string.header_back),
                )
            }
        }
    }
}