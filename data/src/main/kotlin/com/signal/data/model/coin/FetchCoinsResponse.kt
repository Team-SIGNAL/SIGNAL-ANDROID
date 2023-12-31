package com.signal.data.model.coin

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.CoinsEntity

data class FetchCoinsResponse(
    @SerializedName("coin_list") val coins: List<Coin>
) {
    data class Coin(
        @SerializedName("coin") val coin: Long,
        @SerializedName("activity") val activity: String,
        @SerializedName("create_date") val createDate: String,
    )
}

fun FetchCoinsResponse.toEntity() = CoinsEntity(coinsEntity = this.coins.map { it.toEntity() })

private fun FetchCoinsResponse.Coin.toEntity() = CoinsEntity.CoinEntity(
    coin = coin,
    activity = activity,
    createDate = createDate,
)