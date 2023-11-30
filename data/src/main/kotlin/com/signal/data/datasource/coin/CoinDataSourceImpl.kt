package com.signal.data.datasource.coin

import com.signal.data.api.CoinApi
import com.signal.data.model.coin.CreateCoinRequest
import com.signal.data.model.coin.CreateCoinResponse
import com.signal.data.model.coin.FetchCoinsResponse
import com.signal.data.util.ExceptionHandler

class CoinDataSourceImpl(
    private val coinApi: CoinApi,
) : CoinDataSource {
    override suspend fun createCoin(createCoinRequest: CreateCoinRequest) =
        ExceptionHandler<CreateCoinResponse>().httpRequest {
            coinApi.createCoin(createCoinRequest = createCoinRequest)
        }.sendRequest()

    override suspend fun fetchCoins() = ExceptionHandler<FetchCoinsResponse>().httpRequest {
        coinApi.fetchCoins()
    }.sendRequest()
}