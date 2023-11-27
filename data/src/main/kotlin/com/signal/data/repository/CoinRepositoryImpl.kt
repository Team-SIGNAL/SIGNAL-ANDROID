package com.signal.data.repository

import com.signal.data.datasource.coin.CoinDataSource
import com.signal.data.model.coin.CreateCoinRequest
import com.signal.data.model.coin.toEntity
import com.signal.domain.entity.CoinsEntity
import com.signal.domain.entity.CreateCoinEntity
import com.signal.domain.enums.Coin
import com.signal.domain.repository.CoinRepository

class CoinRepositoryImpl(
    private val coinDataSource: CoinDataSource,
) : CoinRepository {

    override suspend fun createCoin(coin: Long, type: Coin): CreateCoinEntity =
        coinDataSource.createCoin(
            CreateCoinRequest(
                coin = coin,
                type = type,
            )
        ).toEntity()

    override suspend fun fetchCoin(): CoinsEntity = coinDataSource.fetchCoins().toEntity()
}