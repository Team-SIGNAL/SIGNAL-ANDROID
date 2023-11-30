package com.signal.domain.entity

data class CoinsEntity(
    val coinsEntity: List<CoinEntity>,
) {
    data class CoinEntity(
        val coin: Long,
        val activity: String,
        val createDate: String,
    )
}
