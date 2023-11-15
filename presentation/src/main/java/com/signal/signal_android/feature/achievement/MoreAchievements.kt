package com.signal.signal_android.feature.achievement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor

internal data class _Achievement(
    val message: String,
)

// TODO 더미
private val achievements = listOf(
    _Achievement(
        message = "500 코인 획득!",
    ),
    _Achievement(
        message = "123 코인 획득",
    ),
)

@Composable
internal fun MoreAchievements(
    moveToBack: () -> Unit,
) {
    Column {
        Header(
            title = stringResource(id = R.string.achievement),
            onLeadingClicked = { moveToBack() },
        )
        Achievements()
    }
}

@Composable
private fun Achievements() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(achievements) {
            Achievement(message = it.message)
        }

    }
}

@Composable
private fun Achievement(
    message: String,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    spotColor = SignalColor.Primary100,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp),
                )
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = SignalColor.Gray100,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(
                    vertical = 23.dp,
                    horizontal = 21.dp,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SignalColor.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyLarge(text = message)
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = stringResource(
                        id = R.string.achievement_image
                    ),
                )
            }
        }
    }
}