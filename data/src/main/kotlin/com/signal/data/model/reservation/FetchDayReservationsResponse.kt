package com.signal.data.model.reservation

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.enums.ReservationStatus
import java.util.UUID

data class FetchDayReservationsResponse(
    @SerializedName("reservation_list") val reservations: List<Reservations>
) {
    data class Reservations(
        @SerializedName("id") val reservationId: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("reservation_status") val reservationStatus: ReservationStatus,
    )
}

fun FetchDayReservationsResponse.toEntity() =
    FetchDayReservationsEntity(reservations = this.reservations.map { it.toEntity() })

private fun FetchDayReservationsResponse.Reservations.toEntity() =
    FetchDayReservationsEntity.DayReservationsEntity(
        id = this.reservationId,
        name = this.name,
        reservationStatus = this.reservationStatus,
    )