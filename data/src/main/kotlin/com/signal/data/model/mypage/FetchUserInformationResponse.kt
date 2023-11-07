package com.signal.data.model.mypage

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class FetchUserInformationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("birth") val birth: LocalDate,
    @SerializedName("profile") val profile: String,
)