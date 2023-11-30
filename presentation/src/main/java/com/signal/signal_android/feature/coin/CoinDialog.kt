package com.signal.signal_android.feature.coin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.domain.enums.Coin
import com.signal.domain.enums.Coin.COMMENT
import com.signal.domain.enums.Coin.DIARY
import com.signal.domain.enums.Coin.FEED
import com.signal.domain.enums.Coin.RESERVATION
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle

@Composable
internal fun CoinDialog(
    coin: Coin,
    coinCount: Long,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(
                horizontal = 55.dp,
                vertical = 30.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.ic_coin),
            contentDescription = stringResource(id = R.string.coin_image),
        )
        Spacer(modifier = Modifier.height(30.dp))
        SubTitle(
            text = stringResource(
                id = when (coin) {
                    COMMENT -> R.string.get_1_coin
                    FEED -> R.string.get_2_coin
                    DIARY -> R.string.get_3_coin
                    RESERVATION -> R.string.get_4_coin
                }
            ),
        )
        Spacer(modifier = Modifier.height(6.dp))
        Body(text = "현재 보유 코인 : $coinCount")
        Spacer(modifier = Modifier.height(40.dp))
        SignalFilledButton(
            text = stringResource(id = R.string.header_back),
            onClick = onClick,
        )
    }
}