package com.signal.signal_android.feature.reservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.signal.domain.enums.Coin
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable
import com.signal.signal_android.feature.coin.CoinDialog
import com.signal.signal_android.feature.coin.CoinSideEffect
import com.signal.signal_android.feature.coin.CoinViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateReservation(
    moveToBack: () -> Unit,
    moveToReservation: () -> Unit,
    hospitalId: UUID,
    reservationViewModel: ReservationViewModel = koinViewModel(),
    coinViewModel: CoinViewModel = koinViewModel(),
) {
    val state by reservationViewModel.state.collectAsState()
    val coinState by coinViewModel.state.collectAsState()

    val selectedHour by remember { mutableIntStateOf(0) }
    val selectedMinute by remember { mutableIntStateOf(0) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
    )

    var isShowDateDialog by remember { mutableStateOf(false) }
    var isShowTimeDialog by remember { mutableStateOf(false) }
    var showCoinDialog by remember { mutableStateOf(false) }

    val selectedMillis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    LaunchedEffect(selectedMillis) {
        val localDateTime =
            Instant.ofEpochMilli(selectedMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        reservationViewModel.setDate(localDateTime.toString())
    }

    LaunchedEffect(Unit) {
        reservationViewModel.setHospitalId(hospitalId = hospitalId)
        reservationViewModel.sideEffect.collect {
            when (it) {
                is ReservationSideEffect.CreateReservationSuccess -> {
                    coinViewModel.createCoin(
                        coin = 4,
                        type = Coin.RESERVATION,
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        coinViewModel.sideEffect.collect {
            when (it) {
                is CoinSideEffect.Success -> {
                    showCoinDialog = true
                }
            }
        }
    }

    if (showCoinDialog) {
        Dialog(onDismissRequest = { showCoinDialog = false }) {
            CoinDialog(
                coin = Coin.RESERVATION,
                coinCount = coinState.createCoinEntity.coinCount,
                onClick = moveToReservation,
            )
        }
    }

    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
        )
    ) {
        Header(
            title = stringResource(id = R.string.reservation_clinic),
            onLeadingClicked = moveToBack,
        )
        Column(
            modifier = Modifier.padding(
                vertical = 8.dp,
            )
        ) {
            ReservationDialog(
                datePickerState = { datePickerState },
                timePickerState = { timePickerState },
                isShowDateDialog = { isShowDateDialog },
                isShowTimeDialog = { isShowTimeDialog },
                showDateDialog = { isShowDateDialog = !isShowDateDialog },
                showTimeDialog = {
                    reservationViewModel.setTime("${timePickerState.hour}:${timePickerState.minute}")
                    isShowTimeDialog = !isShowTimeDialog
                },
                date = { state.date },
                time = { state.time },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SignalTextField(
                modifier = Modifier.fillMaxHeight(0.5f),
                value = state.reason,
                onValueChange = reservationViewModel::setReason,
                hint = stringResource(id = R.string.create_reservation_reason_hint),
                title = stringResource(id = R.string.create_reservation_reason),
                alignment = Alignment.Top,
                showLength = true,
                singleLine = false,
                maxLength = 100,
            )
            Spacer(modifier = Modifier.weight(1f))
            SignalFilledButton(
                text = stringResource(id = R.string.my_page_secession_check),
                onClick = { reservationViewModel.createReservation() },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReservationDialog(
    datePickerState: () -> DatePickerState,
    timePickerState: () -> TimePickerState,
    isShowDateDialog: () -> Boolean,
    isShowTimeDialog: () -> Boolean,
    showDateDialog: () -> Unit,
    showTimeDialog: () -> Unit,
    date: () -> String,
    time: () -> String,
) {
    if (isShowDateDialog()) {
        DatePickerDialog(
            onDismissRequest = showDateDialog,
            confirmButton = {
                SignalFilledButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.my_page_secession_check),
                    onClick = showDateDialog,
                )
            },
        ) {
            DatePicker(
                state = datePickerState(),
                colors = DatePickerDefaults.colors(
                    selectedYearContainerColor = SignalColor.Primary100,
                    selectedDayContainerColor = SignalColor.Primary100,
                    todayDateBorderColor = SignalColor.Primary100,
                    currentYearContentColor = SignalColor.Primary100,
                    containerColor = SignalColor.Gray100,
                ),
            )
        }
    }
    if (isShowTimeDialog()) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SignalColor.White,
                    shape = RoundedCornerShape(size = 12.dp),
                ),
            onDismissRequest = showTimeDialog,
        ) {
            Column(
                modifier = Modifier
                    .background(color = SignalColor.White)
                    .padding(
                        top = 28.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 12.dp,
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TimePicker(
                    state = timePickerState(),
                    colors = TimePickerDefaults.colors(
                        clockDialColor = SignalColor.White,
                        clockDialSelectedContentColor = SignalColor.White,
                        containerColor = SignalColor.Primary100,
                        periodSelectorBorderColor = SignalColor.Primary100,
                        periodSelectorSelectedContainerColor = SignalColor.Primary100,
                        periodSelectorSelectedContentColor = SignalColor.White,
                        selectorColor = SignalColor.Primary100,
                    ),
                )
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SignalFilledButton(
                        text = stringResource(id = R.string.my_page_secession_check),
                        onClick = showTimeDialog,
                    )
                }
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        BodyLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.create_reservation_date),
            color = SignalColor.Gray500,
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SignalColor.Gray600,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = showDateDialog,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyLarge(text = date())
        Image(
            modifier = Modifier.signalClickable(onClick = showDateDialog),
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = stringResource(id = R.string.text_field_icon),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        BodyLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.create_reservation_time),
            color = SignalColor.Gray500,
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SignalColor.Gray600,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = showTimeDialog,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyLarge(text = time())
        Image(
            modifier = Modifier.signalClickable(onClick = showTimeDialog),
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = stringResource(id = R.string.text_field_icon),
        )
    }
}