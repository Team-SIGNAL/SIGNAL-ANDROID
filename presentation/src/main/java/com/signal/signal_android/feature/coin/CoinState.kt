package com.signal.signal_android.feature.coin

import com.signal.domain.entity.CoinsEntity
import com.signal.domain.entity.CreateCoinEntity
import com.signal.domain.enums.Coin

data class CoinState(
    val createCoinEntity: CreateCoinEntity,
    val coin: Coin,
    val coins: List<CoinsEntity.CoinEntity>,
    val isCoinEmpty: Boolean,
) {
    companion object {
        fun getDefaultState() = CoinState(
            createCoinEntity = CreateCoinEntity(
                coinCount = 0L,
            ),
            coin = Coin.COMMENT,
            coins = listOf(),
            isCoinEmpty = true,
        )
    }
}