package com.signal.signal_android.feature.reservation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.entity.FetchReservationDetailsEntity
import com.signal.domain.enums.ReservationStatus
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class ReservationState(
    val dayReservationEntity: SnapshotStateList<FetchDayReservationsEntity.DayReservationEntity>,
    val hospitals: List<FetchHospitalsEntity.HospitalEntity>,
    val reservationDetailsEntity: FetchReservationDetailsEntity,
    val isDayReservationsEmpty: Boolean,
    val isHospitalsEmpty: Boolean,
    val date: String,
    val reason: String,
    val reservationTime: String,
    val time: String,
    val reservationStatus: ReservationStatus,
    val reservationId: UUID,
    val hospitalId: UUID,
) {
    companion object {
        fun getDefaultState() = ReservationState(
            dayReservationEntity = mutableStateListOf(),
            hospitals = listOf(),
            reservationDetailsEntity = FetchReservationDetailsEntity(
                image = null,
                name = "",
                address = "",
                reservationStatus = ReservationStatus.WAIT,
                reason = "",
                date = "",
                phone = "",
            ),
            isDayReservationsEmpty = true,
            isHospitalsEmpty = true,
            date = LocalDate.now().toString(),
            reason = "",
            reservationTime = LocalTime.now().toString(),
            time = "${LocalTime.now().hour}:${LocalTime.now().minute}",
            reservationStatus = ReservationStatus.WAIT,
            reservationId = UUID.randomUUID(),
            hospitalId = UUID.randomUUID()
        )
    }

}