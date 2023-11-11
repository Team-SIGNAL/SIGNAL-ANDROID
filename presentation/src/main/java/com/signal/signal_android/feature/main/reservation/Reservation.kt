package com.signal.signal_android.feature.main.reservation

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
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
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal data class _Reservations(
    val hospital: String,
    val reservationStatus: ReservationStatus,
)

// TODO 더미
internal val reservations = listOf(
    _Reservations(
        hospital = "ㅁㄹㅁㄹㅁㄹㅁㄹㅁㄹ병원",
        reservationStatus = ReservationStatus.APPROVE,
    ), _Reservations(
        hospital = "ㅁㄴㅇㄹㅁㄴㅇㄹㄹㄹ병원",
        reservationStatus = ReservationStatus.REFUSE,
    ), _Reservations(
        hospital = "ㅁㄴㅇㅂㅈㄷㄱㅂㅈㄷㄱㄹ병원",
        reservationStatus = ReservationStatus.STAND_BY,
    )
)

internal enum class ReservationStatus {
    APPROVE, STAND_BY, REFUSE
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Reservation(
    moveToCreateReservation: () -> Unit,
) {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    val date = Date()

    var yearState by remember { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by remember { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by remember { mutableStateOf(formatter.format(date).split("-").last()) }

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
                onClick = {/*TODO*/ },
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
            Reservations(reservations = reservations)
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
private fun Reservations(reservations: List<_Reservations>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(reservations) {
            ReservationItems(
                hospital = it.hospital,
                reservationStatus = it.reservationStatus,
            )
        }

    }

}

@Composable
private fun ReservationItems(
    hospital: String,
    reservationStatus: ReservationStatus,
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
                onClick = { /* TODO Bottom sheet */ },
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

                ReservationStatus.STAND_BY -> Body(
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