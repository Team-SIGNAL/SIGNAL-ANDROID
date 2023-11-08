package com.signal.signal_android.feature.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.views.chart.line.lineChart
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun Home(
    moveToReservation: () -> Unit,
) {
    Column {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SubTitle(text = stringResource(id = R.string.home))
                AsyncImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .signalClickable(
                            rippleEnabled = true,
                            onClick = {},
                        ),
                    model = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png",
                    contentDescription = stringResource(id = R.string.my_page_profile_image),
                )
            }
            Spacer(modifier = Modifier.height(34.dp))
            HomeChart(
                onNext = {},
                currentView = "월별",
                onPrevious = {},
            )
            Spacer(modifier = Modifier.height(34.dp))
        }
        Column(
            modifier = Modifier
                .background(SignalColor.Gray200)
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                    ),
                )
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            BodyLarge2(text = stringResource(id = R.string.home_activity))
            Spacer(modifier = Modifier.height(8.dp))
            ActivityCard(
                title = "우울증 자가진단",
                description = "최근 진행 : 2023년 10월 21일",
                onClick = {},
            )
            Spacer(modifier = Modifier.height(14.dp))
            ReservationCard(
                title = stringResource(id = R.string.reservation),
                onClick = moveToReservation,
            )
            Spacer(modifier = Modifier.height(14.dp))
            ActivityCard(
                title = stringResource(id = R.string.home_activity_ongoing),
                description = "총 17개",
                onClick = { /*TODO*/ },
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                OngoingActivity(
                    title = "댓글 작성 완료",
                    max = 4f,
                    current = 2f,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun OngoingActivity(
    title: String,
    max: Float,
    current: Float,
) {
    Body(text = title)
    Spacer(modifier = Modifier.height(8.dp))
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(CircleShape),
        progress = (current / max),
        color = SignalColor.Primary100,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Body(
        text = "${current.toInt()}/${max.toInt()}",
        color = SignalColor.Gray500,
    )
}

@Composable
private fun HomeChart(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    currentView: String,
) {
    val chartEntryModel = entryModelOf(1, 2, 3, 4, 5, 6, 7)
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyStrong(text = stringResource(id = R.string.home_chart))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(90f)
                    .signalClickable(onClick = onPrevious),
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = stringResource(id = R.string.home_previous),
                tint = SignalColor.Gray500,
            )
            Body(text = currentView)
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(270f)
                    .signalClickable(onClick = onNext),
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = stringResource(id = R.string.next),
                tint = SignalColor.Gray500,
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Chart(
        chart = lineChart(context = context),
        model = chartEntryModel,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(),
    )
}

@Composable
private fun ReservationCard(
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = 11.dp,
                bottom = 11.dp,
                start = 14.dp,
                end = 14.dp,
            ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BodyStrong(modifier = Modifier.weight(1f), text = title)
            Icon(
                modifier = Modifier.rotate(270f),
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = stringResource(id = R.string.next),
            )
        }
    }
}


@Composable
private fun ActivityCard(
    title: String,
    description: String?,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = 12.dp,
                bottom = 10.dp,
                start = 14.dp,
                end = 14.dp,
            ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                BodyStrong(text = title)
                if (description != null) {
                    Body(
                        text = description,
                        color = SignalColor.Gray500,
                    )
                }
            }
            Icon(
                modifier = Modifier.rotate(270f),
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = stringResource(id = R.string.next),
            )
        }
        content?.invoke()
    }
}
