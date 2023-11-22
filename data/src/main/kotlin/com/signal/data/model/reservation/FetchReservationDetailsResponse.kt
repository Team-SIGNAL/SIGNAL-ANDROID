package com.signal.data.model.reservation

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchReservationDetailsEntity
import com.signal.domain.enums.ReservationStatus

data class FetchReservationDetailsResponse(
    @SerializedName("image") val image: String?,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("reservation_status") val reservationStatus: ReservationStatus,
    @SerializedName("reason") val reason: String,
    @SerializedName("date") val date: String,
    @SerializedName("phone") val phone: String,
)

fun FetchReservationDetailsResponse.toEntity() = FetchReservationDetailsEntity(
    image = this.image,
    name = this.name,
    address = this.address,
    reservationStatus = this.reservationStatus,
    reason = this.reason,
    date = this.date,
    phone = this.phone,
)