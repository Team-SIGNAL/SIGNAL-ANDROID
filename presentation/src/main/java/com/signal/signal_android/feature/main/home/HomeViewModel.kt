package com.signal.signal_android.feature.main.home

import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.DiagnosisHistoryEntity
import com.signal.domain.enums.ChartViewType
import com.signal.domain.usecase.users.GetAccountIdUseCase
import com.signal.domain.usecase.users.GetDiagnosisHistoriesUseCase
import com.signal.domain.usecase.users.GetUserInformationUseCase
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getDiagnosisHistoriesUseCase: GetDiagnosisHistoriesUseCase,
    private val getAccountIdUseCase: GetAccountIdUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {
    private val diagnosisHistories: MutableList<DiagnosisHistoryEntity> = mutableListOf()
    private val diagnosisHistoryUiModels: MutableList<DiagnosisHistoryUiModel> = mutableListOf()

    private lateinit var array: Array<Pair<Float, Float>>

    init {
        getAccountId()
        getUserInformation()
    }

    private fun getAccountId() {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountIdUseCase().onSuccess {
                getDiagnosisHistories(userId = it)
            }
        }
    }

    private fun getDiagnosisHistories(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getDiagnosisHistoriesUseCase(userId = userId).onSuccess { it ->
                diagnosisHistories.addAll(it)
                diagnosisHistories.forEach {
                    diagnosisHistoryUiModels.add(
                        DiagnosisHistoryUiModel(
                            xLabel = it.day.toFloat(),
                            score = it.score.toFloat(),
                        )
                    )
                }

                setState(
                    state.value.copy(
                        diagnosisHistoryUiModels = diagnosisHistoryUiModels,
                        lastDiagnosisDate = diagnosisHistories.lastOrNull()?.run {
                            "${year}-${month}-${day.toString().padStart(2, '0')}"
                        }?.toLastDiagnosisDate()
                            ?: "",
                    )
                )
            }
        }
    }

    private fun String.toLastDiagnosisDate() = StringBuilder().apply {
        append(this@toLastDiagnosisDate.substring(0..3))
        append("년 ")
        append(this@toLastDiagnosisDate.substring(5..6))
        append("월 ")
        append(this@toLastDiagnosisDate.substring(8..9))
        append("일 ")
    }.toString()

    internal fun nextChartViewType() {
        with(state.value) {
            val currentChartViewTypeIndex = chartViewType.ordinal
            setState(
                copy(
                    chartViewType = if (currentChartViewTypeIndex != ChartViewType.values().lastIndex) {
                        ChartViewType.values()[currentChartViewTypeIndex + 1]
                    } else {
                        ChartViewType.values().first()
                    }
                )
            )
        }
        setDiagnosisHistoriesByChartViewType()
    }

    private fun setDiagnosisHistoriesByChartViewType() {
        array = Array(
            when (state.value.chartViewType) {
                ChartViewType.DAY -> 32
                ChartViewType.WEEK -> 53
                ChartViewType.MONTH -> 13
                ChartViewType.YEAR -> 2100
            }
        ) { 0f to 0f }

        diagnosisHistories.forEach {
            val index = when (state.value.chartViewType) {
                ChartViewType.DAY -> it.day
                ChartViewType.WEEK -> it.week
                ChartViewType.MONTH -> it.month
                ChartViewType.YEAR -> it.year
            }

            array[index] = Pair(
                first = array[index].first + 1f,
                second = array[index].second + it.score.toFloat()
            )
        }

        diagnosisHistoryUiModels.clear()
        array.forEachIndexed { index, pair ->
            if (pair.first != 0f) {
                diagnosisHistoryUiModels.add(
                    DiagnosisHistoryUiModel(
                        xLabel = index.toFloat(),
                        score = pair.second / pair.first,
                    )
                )
            }
        }
    }

    internal fun previousChartViewType() {
        with(state.value) {
            val currentChartViewTypeIndex = chartViewType.ordinal
            setState(
                copy(
                    chartViewType = if (currentChartViewTypeIndex != 0) {
                        ChartViewType.values()[currentChartViewTypeIndex - 1]
                    } else {
                        ChartViewType.values().last()
                    }
                )
            )
        }
        setDiagnosisHistoriesByChartViewType()
    }

    private fun getUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInformationUseCase().onSuccess {
                setState(state.value.copy(profile = it.imageUrl))
            }
        }
    }
}
