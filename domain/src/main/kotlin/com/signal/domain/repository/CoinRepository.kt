package com.signal.domain.repository

import com.signal.domain.entity.CoinsEntity
import com.signal.domain.entity.CreateCoinEntity
import com.signal.domain.enums.Coin

interface CoinRepository {
    suspend fun createCoin(
        coin: Long,
        type: Coin,
    ): CreateCoinEntity

    suspend fun fetchCoin(): CoinsEntity
}