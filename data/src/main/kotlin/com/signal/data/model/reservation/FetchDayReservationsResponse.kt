package com.signal.data.model.reservation

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.enums.ReservationStatus

data class FetchDayReservationsResponse(
    @SerializedName("reservation_list") val reservations: List<Reservations>
) {
    data class Reservations(
        @SerializedName("name") val name: String,
        @SerializedName("is_reservation") val isReservation: ReservationStatus,
    )
}

fun FetchDayReservationsResponse.toEntity() =
    FetchDayReservationsEntity(reservations = this.reservations.map { it.toEntity() })

private fun FetchDayReservationsResponse.Reservations.toEntity() =
    FetchDayReservationsEntity.DayReservationsEntity(
        name = this.name, isReservation = this.isReservation
    )