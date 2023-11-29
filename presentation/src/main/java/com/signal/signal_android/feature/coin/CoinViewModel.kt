package com.signal.signal_android.feature.coin

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.CoinsEntity
import com.signal.domain.entity.CreateCoinEntity
import com.signal.domain.enums.Coin
import com.signal.domain.repository.CoinRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinRepository: CoinRepository,
) : BaseViewModel<CoinState, CoinSideEffect>(CoinState.getDefaultState()) {

    private val _coins: SnapshotStateList<CoinsEntity.CoinEntity> = mutableStateListOf()

    internal fun createCoin(
        coin: Long,
        type: Coin,
    ) {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    coinRepository.createCoin(
                        coin = coin,
                        type = type,
                    )
                }.onSuccess {
                    setState(
                        copy(
                            createCoinEntity = CreateCoinEntity(coinCount = it.coinCount)
                        )
                    )
                    postSideEffect(CoinSideEffect.Success)
                }
            }
        }
    }

    internal fun fetchCoins() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    coinRepository.fetchCoin()
                }.onSuccess {
                    _coins.clear()
                    _coins.addAll(it.coinsEntity)
                    setState(
                        copy(
                            coins = _coins,
                            isCoinEmpty = _coins.isEmpty(),
                        )
                    )
                }.onFailure {
                    _coins.clear()
                    setState(copy(isCoinEmpty = _coins.isEmpty()))
                }
            }
        }
    }
}