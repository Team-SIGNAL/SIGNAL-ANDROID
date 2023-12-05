package com.signal.signal_android.feature.reservation

import android.widget.CalendarView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.enums.ReservationStatus
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun Reservation(
    moveToBack: () -> Unit,
    moveToCreateReservation: () -> Unit,
    reservationViewModel: ReservationViewModel = koinViewModel(),
) {
    val state by reservationViewModel.state.collectAsState()
    val details = state.reservationDetailsEntity

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    val date = Date()

    var yearState by remember { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by remember { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by remember { mutableStateOf(formatter.format(date).split("-").last()) }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        reservationViewModel.fetchReservationDetails()

        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            contentColor = SignalColor.White,
            containerColor = SignalColor.White,
        ) {
            SheetContent(
                image = details.image,
                hospital = details.name,
                address = details.address,
                reservationStatus = details.reservationStatus,
                date = details.date,
                phone = details.phone,
                reason = details.reason,
            )
        }
    }

    LaunchedEffect(Unit) {
        reservationViewModel.fetchDayReservations()
    }

    LaunchedEffect("$yearState-$monthState-$dayState") {
        reservationViewModel.setDate("$yearState-$monthState-${dayState.padStart(2, '0')}")
        reservationViewModel.fetchDayReservations()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Header(
                title = stringResource(id = R.string.reservation),
                onLeadingClicked = moveToBack,
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AndroidView(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = SignalColor.Primary100,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth(),
                        factory = { CalendarView(it) },
                    ) { calendarView ->
                        val selectedDate = "${yearState}-${monthState}-${dayState}"
                        calendarView.date = formatter.parse(selectedDate)!!.time

                        calendarView.setOnDateChangeListener { _, year, month, day ->
                            yearState = year.toString()
                            monthState = (month + 1).toString()
                            dayState = day.toString()
                        }
                    }
                }
                stickyHeader {
                    ReservationHeader(
                        month = monthState,
                        day = dayState,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Reservations(
                reservations = state.dayReservationEntity,
                onClick = {
                    reservationViewModel.setReservationId(it)
                    showBottomSheet = true
                },
            )
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = moveToCreateReservation,
            backgroundColor = SignalColor.Primary100,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.feed_post),
                tint = SignalColor.White,
            )
        }
    }
}

@Composable
private fun ReservationHeader(
    month: String,
    day: String,
) {
    Spacer(modifier = Modifier.height(26.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyStrong(
            text = month + "월 " + day + "일",
            color = SignalColor.Black,
        )
    }
}

@Composable
private fun Reservations(
    reservations: List<FetchDayReservationsEntity.DayReservationEntity>,
    onClick: (reservationId: UUID) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(reservations) {
            ReservationItems(
                hospital = it.name,
                reservationStatus = it.reservationStatus,
                onClick = { onClick(it.id) },
            )
        }
    }
}

@Composable
private fun ReservationItems(
    hospital: String,
    reservationStatus: ReservationStatus,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            BodyStrong(
                text = hospital,
                color = SignalColor.Black,
            )
            when (reservationStatus) {
                ReservationStatus.APPROVE -> Body(
                    text = stringResource(id = R.string.reservation_approve),
                    color = SignalColor.Primary100,
                )

                ReservationStatus.WAIT -> Body(
                    text = stringResource(id = R.string.reservation_stand_by),
                    color = SignalColor.Gray500,
                )

                ReservationStatus.REFUSE -> Body(
                    text = stringResource(id = R.string.reservation_refuse),
                    color = SignalColor.Error,
                )
            }
        }
    }
}

@Composable
private fun SheetContent(
    image: String?,
    hospital: String,
    address: String,
    reservationStatus: ReservationStatus,
    date: String,
    phone: String,
    reason: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp, horizontal = 40.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                model = image ?: R.drawable.ic_default_hospital,
                contentDescription = stringResource(id = R.string.reservation_hospital_image),
            )
            Column {
                BodyStrong(text = hospital)
                Body2(
                    text = address,
                    color = SignalColor.Gray500,
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        when (reservationStatus) {
            ReservationStatus.APPROVE -> Body(
                text = stringResource(id = R.string.reservation_approve),
                color = SignalColor.Primary100,
            )

            ReservationStatus.WAIT -> Body(
                text = stringResource(id = R.string.reservation_stand_by),
                color = SignalColor.Gray500,
            )

            ReservationStatus.REFUSE -> Body(
                text = stringResource(id = R.string.reservation_refuse),
                color = SignalColor.Error,
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Column(
            modifier = Modifier
                .border(
                    width = 0.4.dp, color = SignalColor.Primary100,
                    shape = RoundedCornerShape(4.dp),
                )
                .fillMaxWidth(),
        ) {
            Body(
                modifier = Modifier.padding(
                    start = 6.dp,
                    top = 6.dp,
                ),
                text = stringResource(id = R.string.reservation_date),
                color = SignalColor.Gray500,
            )
            Body(
                modifier = Modifier.padding(
                    start = 6.dp,
                    top = 4.dp,
                ),
                text = date,
                color = SignalColor.Black,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Body(
                modifier = Modifier.padding(
                    start = 6.dp,
                    top = 12.dp,
                ),
                text = stringResource(id = R.string.phone_number),
                color = SignalColor.Gray500,
            )
            Body(
                modifier = Modifier.padding(
                    start = 6.dp,
                    top = 4.dp,
                    bottom = 6.dp,
                ),
                text = phone,
                color = SignalColor.Black,
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Body(
            text = stringResource(id = R.string.create_reservation_reason),
            color = SignalColor.Gray500,
        )
        Body(
            text = reason,
            color = SignalColor.Black,
        )
    }
}