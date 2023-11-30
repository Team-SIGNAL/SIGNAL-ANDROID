package com.signal.data.model.coin

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.CreateCoinEntity

data class CreateCoinResponse(
    @SerializedName("coin_count") val coinCount: Long,
)

fun CreateCoinResponse.toEntity() = CreateCoinEntity(
    coinCount = this.coinCount
)
