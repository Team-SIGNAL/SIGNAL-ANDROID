package com.signal.signal_android.feature.reservation

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import org.koin.androidx.compose.koinViewModel
import java.util.UUID


@Composable
internal fun Hospital(
    moveToBack: () -> Unit,
    moveToCreateReservation: (hospitalId: UUID) -> Unit,
    reservationViewModel: ReservationViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        reservationViewModel.fetchHospitals()

    }

    val state by reservationViewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = stringResource(id = R.string.hospital_get_hospital_list),
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Body(text = "조회 결과 ${state.hospitals.size}건")
        Spacer(modifier = Modifier.height(8.dp))
        Hospitals(
            hospitals = state.hospitals,
            moveToCreateHospital = moveToCreateReservation,
        )
    }
}

@Composable
private fun Hospitals(
    moveToCreateHospital: (hospitalId: UUID) -> Unit,
    hospitals: List<FetchHospitalsEntity.HospitalsEntity>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(hospitals) {
            HospitalItems(
                imageUrl = it.profile,
                hospital = it.name,
                phone = it.phone,
                address = it.address,
                moveToCreateHospital = { moveToCreateHospital(it.id) },
            )
        }
    }
}


@Composable
private fun HospitalItems(
    imageUrl: String?,
    hospital: String,
    phone: String,
    address: String,
    moveToCreateHospital: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
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
                onClick = moveToCreateHospital,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.size(48.dp),
            model = imageUrl ?: R.drawable.ic_default_hospital,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BodyStrong(
                    text = hospital,
                    color = SignalColor.Primary100,
                    overflow = TextOverflow.Ellipsis,
                )
                Body(
                    text = phone,
                    color = SignalColor.Gray500,
                )
            }
            Body(
                text = address,
                color = SignalColor.Gray500,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}