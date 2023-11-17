package com.signal.signal_android.feature.reservation

import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.enums.ReservationStatus
import java.time.LocalDate
import java.time.LocalTime

data class ReservationState(
    val dayReservationsEntity: List<FetchDayReservationsEntity.DayReservationsEntity>,
    val hospitals: List<FetchHospitalsEntity.HospitalsEntity>,
    val isDayReservationsEmpty: Boolean,
    val isHospitalsEmpty: Boolean,
    val image: String?,
    val name: String,
    val phone: String,
    val address: String,
    val date: String,
    val reason: String,
    val reservationDate: String,
    val time: String,
    val reservationStatus: ReservationStatus,
    val reservationId: Long,
) {
    companion object {
        fun getDefaultState() = ReservationState(
            dayReservationsEntity = listOf(),
            hospitals = listOf(),
            isDayReservationsEmpty = true,
            isHospitalsEmpty = true,
            image = null,
            name = "",
            phone = "",
            address = "",
            date = LocalDate.now().toString(),
            reason = "",
            reservationDate = "",
            time = "${LocalTime.now().hour}:${LocalTime.now().minute}",
            reservationStatus = ReservationStatus.STAND_BY,
            reservationId = 0L,
        )
    }

}