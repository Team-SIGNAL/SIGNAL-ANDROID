package com.signal.signal_android.feature.main.diary

import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.FetchAllDiaryEntity
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.entity.FetchMonthDiaryEntity
import com.signal.domain.repository.DiaryRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<DiaryState, DiarySideEffect>(DiaryState.getDefaultState()) {

    private val _allDiaries: MutableList<FetchAllDiaryEntity.AllDiaryEntity> = mutableListOf()
    private val _monthDiaries: MutableList<FetchMonthDiaryEntity.MonthDiaryEntity> = mutableListOf()
    private val _dayDiaries: MutableList<FetchDayDiaryEntity.DayDiaryEntity> = mutableListOf()

    internal fun fetchAllDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    diaryRepository.fetchAllDiary()
                }.onSuccess {
                    _allDiaries.addAll(it.allDiaryEntity)
                    setState(
                        copy(
                            allDiaries = _allDiaries,
                            isAllDiariesEmpty = _allDiaries.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isAllDiariesEmpty = _allDiaries.isEmpty()))
                }
            }
        }
    }

    internal fun fetchMonthDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    diaryRepository.fetchMonthDiary(date = date)
                }.onSuccess {
                    _monthDiaries.addAll(it.monthDiaryEntity)
                    setState(
                        copy(
                            monthDiaries = _monthDiaries,
                            isMonthDiariesEmpty = _monthDiaries.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isMonthDiariesEmpty = _monthDiaries.isEmpty()))
                }
            }
        }
    }

    internal fun fetchDayDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    diaryRepository.fetchDayDiary(date = date)
                }.onSuccess {
                    _dayDiaries.addAll(it.dayDiaryEntity)
                    setState(
                        copy(
                            dayDiaries = _dayDiaries,
                            isDayDiariesEmpty = _dayDiaries.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isDayDiariesEmpty = _dayDiaries.isEmpty()))
                }
            }
        }
    }

    internal fun createDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.createDiary(
                    title = title,
                    content = content,
                    emotion = emotion,
                    date = date,
                    image = image,
                ).onSuccess {
                    DiarySideEffect.CreateDiarySuccess
                }
            }
        }
    }


}