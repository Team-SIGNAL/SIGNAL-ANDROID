package com.signal.signal_android.feature.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.chart.rememberMarker
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable
import com.signal.signal_android.feature.main.mypage.MyPageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Home(
    moveToReservation: () -> Unit,
    moveToDiagnosisLanding: () -> Unit,
    moveToCoinHistory: () -> Unit,
    homeViewModel: HomeViewModel = koinViewModel(),
    myPageViewModel: MyPageViewModel = koinViewModel(),
) {
    val state by homeViewModel.state.collectAsState()
    val myPageState by myPageViewModel.state.collectAsState()

    var dialogState by remember { mutableStateOf(false) }
    val showDialog = { dialogState = true }
    val hideDialog = { dialogState = false }

    if (dialogState) {
        Dialog(onDismissRequest = { dialogState = false }) {
            ChartInformationDialog()
        }
    }

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
                            rippleEnabled = false,
                            onClick = {},
                        ),
                    model = myPageState.profile ?: R.drawable.ic_profile_image,
                    contentDescription = stringResource(id = R.string.my_page_profile_image),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.height(34.dp))
            Box(contentAlignment = Alignment.Center) {
                HomeChart(
                    onNext = homeViewModel::nextChartViewType,
                    currentView = state.chartViewType.value,
                    onPrevious = homeViewModel::previousChartViewType,
                    showDialog = showDialog,
                    diagnosisHistories = state.diagnosisHistoryUiModels,
                )
                if (state.diagnosisHistoryUiModels.isEmpty()) {
                    Body2(
                        text = stringResource(id = R.string.home_diagnosis_history_null),
                        color = SignalColor.Gray500,
                    )
                }
            }
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
            state.lastDiagnosisDate.run {
                ActivityCard(
                    title = stringResource(id = R.string.diagnosis_title),
                    description = if (this.isBlank()) {
                        null
                    } else {
                        "최근 진행 : ${state.lastDiagnosisDate}"
                    },
                    onClick = moveToDiagnosisLanding,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            ReservationCard(
                title = stringResource(id = R.string.reservation),
                onClick = moveToReservation,
            )
            Spacer(modifier = Modifier.height(14.dp))
            ActivityCard(
                title = stringResource(id = R.string.home_activity_ongoing),
                description = null,
                onClick = { moveToCoinHistory() },
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                OngoingActivity(
                    title = "총 코인 : ${myPageState.coinCount} 🪙",
                    max = 500f,
                    current = "${myPageState.coinCount}".toFloat(),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ChartInformationDialog() {
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
        BodyLarge2(
            text = stringResource(id = R.string.home_information_title),
            color = SignalColor.Black,
        )
        Body(
            modifier = Modifier.padding(bottom = 30.dp),
            text = stringResource(id = R.string.home_information_description),
            color = SignalColor.Gray500,
        )
        ChartInformationText(
            scoreName = stringResource(id = R.string.home_very_high),
            scoreNameColor = SignalColor.Wine,
            score = stringResource(id = R.string.home_very_high_score),
        )
        ChartInformationText(
            scoreName = stringResource(id = R.string.home_high),
            scoreNameColor = SignalColor.Error,
            score = stringResource(id = R.string.home_high_score),
        )
        ChartInformationText(
            scoreName = stringResource(id = R.string.home_normal),
            scoreNameColor = SignalColor.Yellow,
            score = stringResource(id = R.string.home_normal_score),
        )
        ChartInformationText(
            scoreName = stringResource(id = R.string.home_low),
            scoreNameColor = SignalColor.Primary100,
            score = stringResource(id = R.string.home_low_score),
        )
    }
}

@Composable
private fun ChartInformationText(
    scoreName: String,
    scoreNameColor: Color,
    score: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyStrong(
            text = scoreName,
            color = scoreNameColor,
        )
        Body(
            text = score,
            color = SignalColor.Black,
        )
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
    diagnosisHistories: List<DiagnosisHistoryUiModel>,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    showDialog: () -> Unit,
    currentView: String,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BodyStrong(text = stringResource(id = R.string.home_chart))
                IconButton(onClick = showDialog) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_information),
                        contentDescription = stringResource(id = R.string.home_information_icon),
                        tint = SignalColor.Gray500,
                    )
                }
            }
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

        val datasetLineSpec = remember {
            arrayListOf(
                LineChart.LineSpec(
                    lineColor = SignalColor.Primary100.toArgb(),
                    lineBackgroundShader = DynamicShaders.fromBrush(
                        brush = Brush.verticalGradient(
                            listOf(
                                SignalColor.Primary100.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                SignalColor.Primary100.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                            )
                        )
                    ),
                )
            )
        }
        val marker = rememberMarker()
        ProvideChartStyle {
            Chart(
                chart = lineChart(lines = datasetLineSpec),
                model = diagnosisHistories.toChartModel(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
                chartScrollState = rememberChartScrollState(),
                marker = marker,
            )
        }
    }
}

private fun List<DiagnosisHistoryUiModel>.toChartModel() =
    entryModelOf(this.map { FloatEntry(it.xLabel, it.score) })

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
            BodyStrong(
                modifier = Modifier.weight(1f),
                text = title,
            )
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
