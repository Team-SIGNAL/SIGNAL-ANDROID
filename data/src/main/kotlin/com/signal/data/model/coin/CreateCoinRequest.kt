package com.signal.data.model.coin

import com.google.gson.annotations.SerializedName
import com.signal.domain.enums.Coin

data class CreateCoinRequest(
    @SerializedName("coin") val coin: Coin
)