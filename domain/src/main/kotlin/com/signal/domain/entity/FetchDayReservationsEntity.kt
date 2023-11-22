package com.signal.domain.entity

import com.signal.domain.enums.ReservationStatus
import java.util.UUID

data class FetchDayReservationsEntity(
    val reservations: List<DayReservationEntity>
) {
    data class DayReservationEntity(
        val id: UUID,
        val name: String,
        val reservationStatus: ReservationStatus,
    )
}