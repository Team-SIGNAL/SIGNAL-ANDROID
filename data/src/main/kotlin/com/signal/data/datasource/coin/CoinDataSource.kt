package com.signal.data.datasource.coin

import com.signal.data.model.coin.CreateCoinRequest
import com.signal.data.model.coin.CreateCoinResponse
import com.signal.data.model.coin.FetchCoinsResponse

interface CoinDataSource {
    suspend fun createCoin(createCoinRequest: CreateCoinRequest): CreateCoinResponse
    suspend fun fetchCoins(): FetchCoinsResponse
}