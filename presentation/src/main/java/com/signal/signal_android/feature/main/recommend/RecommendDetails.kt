package com.signal.signal_android.feature.main.recommend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.SignalColor

@Composable
internal fun RecommendDetails(
    moveToBack: () -> Unit,
) {
    val title = ""
    val image: String? = null
    val content = ""

    val intentToUrl = {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = title,
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            if (image != null) {
                Spacer(modifier = Modifier.height(22.dp))
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    model = image,
                    contentDescription = stringResource(id = R.string.feed_details_image),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Body2(
                text = content,
                color = SignalColor.Gray700,
            )
            Spacer(modifier = Modifier.weight(1f))
            SignalFilledButton(
                modifier = Modifier.padding(bottom = 34.dp),
                text = stringResource(id = R.string.recommend_details_move_to_link),
                onClick = intentToUrl,
            )
        }
    }
}