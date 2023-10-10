package com.signal.data.model.signup

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class SignUpRequest(
    @SerializedName("name") val name: String,
    @SerializedName("birth") val birth: LocalDate,
    @SerializedName("phone") val phone: String,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
)
