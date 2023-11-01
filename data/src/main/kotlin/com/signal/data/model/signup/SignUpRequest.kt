package com.signal.data.model.signup

import com.google.gson.annotations.SerializedName
import com.signal.domain.enums.Gender

data class SignUpRequest(
    @SerializedName("name") val name: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("gender") val gender: Gender,
)
