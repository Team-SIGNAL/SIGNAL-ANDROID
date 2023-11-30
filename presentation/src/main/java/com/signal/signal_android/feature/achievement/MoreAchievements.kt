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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.signal.signal_android.feature.main.mypage.MyPageViewModel
import org.koin.androidx.compose.koinViewModel

internal data class _Achievement(
    val message: String?,
    val coin: Long,
)

private val achievements = listOf(
    _Achievement(
        message = "10 코인 획득",
        coin = 10,
    ),
    _Achievement(
        message = "50 코인 획득",
        coin = 50,
    ),
    _Achievement(
        message = "100 코인 획득",
        coin = 100,
    ),
    _Achievement(
        message = "200 코인 획득",
        coin = 200,
    ),
    _Achievement(
        message = "300 코인 획득",
        coin = 300,
    ),
    _Achievement(
        message = "400 코인 획득",
        coin = 400,
    ),
    _Achievement(
        message = "500 코인 획득!",
        coin = 500,
    ),
)

@Composable
internal fun MoreAchievements(
    moveToBack: () -> Unit,
    myPageViewModel: MyPageViewModel = koinViewModel(),
) {
    val state by myPageViewModel.state.collectAsState()

    Column {
        Header(
            title = stringResource(id = R.string.achievement),
            onLeadingClicked = { moveToBack() },
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(achievements) {
                Achievement(
                    message = (if (it.coin <= state.coinCount) {
                        it.message
                    } else {
                        null
                    }),
                )
            }
        }
    }
}

@Composable
private fun Achievement(
    message: String?,
) {
    if (message != null) {
        Row(
            modifier = Modifier.padding(
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
}