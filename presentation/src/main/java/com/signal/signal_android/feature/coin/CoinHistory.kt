package com.signal.signal_android.feature.coin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.domain.entity.CoinsEntity
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CoinHistory(
    moveToBack: () -> Unit,
    coinViewModel: CoinViewModel = koinViewModel(),
) {
    val state by coinViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        coinViewModel.fetchCoins()
    }

    Column {
        Header(
            title = stringResource(id = R.string.coin_history),
            onLeadingClicked = { moveToBack() },
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Coins(
                coins = state.coins,
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun Coins(
    coins: List<CoinsEntity.CoinEntity>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = 16.dp,
            ),
    ) {
        items(coins) {
            CoinItemList(
                coin = it.coin,
                activity = it.activity,
                createDate = it.createDate,
            )
        }
    }
}

@Composable
internal fun CoinItemList(
    coin: Long,
    activity: String,
    createDate: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            BodyStrong(
                text = "${coin}코인 획득!",
                color = SignalColor.Primary100,
            )
            Body(
                text = activity,
                color = SignalColor.Gray500,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Body(
                text = createDate, color = SignalColor.Gray500
            )
        }
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.ic_coin_1k),
            contentDescription = stringResource(id = R.string.coin_image),
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}