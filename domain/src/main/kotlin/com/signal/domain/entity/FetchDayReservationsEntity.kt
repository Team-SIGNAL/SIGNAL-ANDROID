package com.signal.domain.entity

import com.signal.domain.enums.ReservationStatus

data class FetchDayReservationsEntity(
    val reservations: List<DayReservationsEntity>
) {
    data class DayReservationsEntity(
        val name: String,
        val isReservation: ReservationStatus,
    )
}