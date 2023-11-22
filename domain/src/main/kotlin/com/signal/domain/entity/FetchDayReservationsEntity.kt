package com.signal.domain.entity

import com.signal.domain.enums.ReservationStatus
import java.util.UUID

data class FetchDayReservationsEntity(
    val reservations: List<DayReservationsEntity>
) {
    data class DayReservationsEntity(
        val reservationId: UUID,
        val name: String,
        val isReservation: ReservationStatus,
    )
}