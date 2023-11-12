package com.signal.signal_android.feature.main.home

import android.util.Log
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
            Log.d("TEST", it)
            setState(state.value.copy(lastDiagnosisDate = it))
        }.onFailure {
            Log.d("TEST", it.toString())
        }
    }
}
