package com.signal.data.api

import com.signal.data.model.coin.CreateCoinRequest
import com.signal.data.model.coin.FetchCoinsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CoinApi {
    @POST(SignalUrl.Coin.CreateCoin)
    suspend fun createCoin(
        @Body createCoinRequest: CreateCoinRequest,
    )

    @GET(SignalUrl.Coin.FetchCoins)
    suspend fun fetchCoins(): FetchCoinsResponse
}