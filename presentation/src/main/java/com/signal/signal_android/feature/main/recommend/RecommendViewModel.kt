package com.signal.signal_android.feature.main.recommend

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.signal.domain.enums.Category
import com.signal.domain.repository.RecommendRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendViewModel(
    private val recommendRepository: RecommendRepository,
) : BaseViewModel<RecommendState, RecommendSideEffect>(RecommendState.getDefaultState()) {
    internal fun fetchRecommends() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                recommendRepository.fetchRecommends(category = category).onSuccess {
                    setState(copy(recommends = it.recommends.toMutableStateList()))
                }
            }
        }
    }

    internal fun setCategory(category: Category) {
        setState(state.value.copy(category = category))
    }
}