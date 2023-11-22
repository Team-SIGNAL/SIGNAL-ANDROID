package com.signal.domain.entity

import com.signal.domain.enums.ReservationStatus

data class FetchReservationDetailsEntity(
    val image: String?,
    val name: String,
    val address: String,
    val reservationStatus: ReservationStatus,
    val reason: String,
    val date: String,
    val phone: String,
)
