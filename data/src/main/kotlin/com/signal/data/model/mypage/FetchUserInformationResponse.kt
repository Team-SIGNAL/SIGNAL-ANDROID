package com.signal.data.model.mypage

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.UserInformationEntity

data class FetchUserInformationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("profile") val profile: String?,
    @SerializedName("coin_count") val coinCount: Long,
)

fun FetchUserInformationResponse.toEntity() = UserInformationEntity(
    name = this.name,
    phone = this.phone,
    birth = this.birth,
    imageUrl = this.profile,
    coinCount = this.coinCount,
)