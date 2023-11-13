package com.signal.signal_android.feature.main.home

import com.signal.domain.repository.DiagnosisRepository
import com.signal.signal_android.BaseViewModel

internal class HomeViewModel(
    private val diagnosisRepository: DiagnosisRepository,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {
    init {
        getLastDiagnosisDate()
    }

    private fun getLastDiagnosisDate() {
        diagnosisRepository.getLastDiagnosisDate().onSuccess {
            setState(state.value.copy(lastDiagnosisDate = it))
        }.onFailure { }
    }
}
