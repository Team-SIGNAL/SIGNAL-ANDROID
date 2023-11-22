package com.signal.data.model.reservation

import com.google.gson.annotations.SerializedName

data class CreateReservationRequest(
    @SerializedName("reason") val reason: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
)