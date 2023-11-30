package com.signal.data.repository

import com.signal.data.datasource.coin.CoinDataSource
import com.signal.data.model.coin.CreateCoinRequest
import com.signal.data.model.coin.toEntity
import com.signal.domain.enums.Coin
import com.signal.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val coinDataSource: CoinDataSource,
) : CoinRepository {

    override suspend fun createCoin(
        coin: Long,
        type: Coin,
    ) = coinDataSource.createCoin(
        CreateCoinRequest(
            coin = coin,
            type = type,
        )
    ).toEntity()

    override suspend fun fetchCoin() = coinDataSource.fetchCoins().toEntity()
}