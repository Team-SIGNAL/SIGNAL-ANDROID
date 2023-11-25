package com.signal.signal_android.feature.main.recommend

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.SignalColor
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
internal fun RecommendDetails(
    moveToBack: () -> Unit,
    recommendId: UUID?,
    recommendViewModel: RecommendViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    val state by recommendViewModel.state.collectAsState()
    val details = state.details

    LaunchedEffect(Unit) {
        with(recommendViewModel) {
            recommendId?.run {
                setRecommendId(recommendId = this)
            }
            fetchRecommendDetails()
        }
    }

    val intentToUrl: () -> Unit = {
        details.link?.run {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = details.title,
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            if (details.image != null) {
                Spacer(modifier = Modifier.height(22.dp))
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    model = details.image,
                    contentDescription = stringResource(id = R.string.feed_details_image),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Body2(
                text = details.content,
                color = SignalColor.Gray700,
            )
            Spacer(modifier = Modifier.weight(1f))
            SignalFilledButton(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 34.dp,
                ),
                text = stringResource(id = R.string.recommend_details_move_to_link),
                onClick = intentToUrl,
            )
        }
    }
}