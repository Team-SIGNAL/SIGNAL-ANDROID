package com.signal.data.model.coin

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.CoinsEntity

data class FetchCoinsResponse(
    @SerializedName("coin_list") val coins: List<Coins>
) {
    data class Coins(
        @SerializedName("coin") val coin: Long,
        @SerializedName("activity") val activity: String,
    )
}

fun FetchCoinsResponse.toEntity() = CoinsEntity(coinsEntity = this.coins.map { it.toEntity() })

private fun FetchCoinsResponse.Coins.toEntity() = CoinsEntity.CoinEntity(
    coin = coin,
    activity = activity,
)