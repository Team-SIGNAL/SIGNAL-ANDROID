package com.signal.signal_android.feature.main.diary

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.DiariesEntity
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.entity.MonthDiaryEntity
import com.signal.domain.enums.Emotion
import com.signal.domain.repository.DiaryRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class DiaryViewModel(
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<DiaryState, DiarySideEffect>(DiaryState.getDefaultState()) {

    private val _diaries: MutableList<DiariesEntity.DiaryEntity> = mutableListOf()
    private val _monthDiaries: MutableList<MonthDiaryEntity.MonthDiaryEntity> = mutableListOf()

    internal fun fetchAllDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    diaryRepository.fetchAllDiary()
                }.onSuccess {
                    _diaries.clear()
                    _diaries.addAll(it.diaryEntity)
                    setState(
                        copy(
                            diaries = _diaries.toMutableStateList(),
                            isAllDiariesEmpty = _diaries.isEmpty(),
                        )
                    )
                }.onFailure {
                    _diaries.clear()
                    setState(copy(isAllDiariesEmpty = _diaries.isEmpty()))
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
                    _diaries.clear()
                    _diaries.addAll(it.diaryEntity)
                    setState(
                        copy(
                            diaries = _diaries.toMutableStateList(),
                            isDayDiariesEmpty = _diaries.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isDayDiariesEmpty = _diaries.isEmpty()))
                }
            }
        }
    }

    internal fun createDiary(imageUrl: String? = null) {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.createDiary(
                    title = title,
                    content = content,
                    emotion = emotion,
                    date = date,
                    image = imageUrl,
                ).onSuccess {
                    postSideEffect(DiarySideEffect.CreateDiarySuccess)
                }.onFailure {
                    if (it is KotlinNullPointerException) {
                        postSideEffect(DiarySideEffect.CreateDiarySuccess)
                    }
                }
            }
        }
    }

    internal fun fetchDiaryDetails() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.fetchDiaryDetails(diaryId = diaryId).onSuccess {
                    setState(
                        copy(
                            diaryDetailsEntity = DiaryDetailsEntity(
                                date = it.date,
                                title = it.title,
                                content = it.content,
                                emotion = it.emotion,
                                image = it.image,
                            )
                        )
                    )
                }
            }
        }
    }

    internal fun deleteDiary() {
        with(state.value) {
            val remove = {
                _diaries.remove(_diaries.find { it.id == diaryId })
                setState(copy(diaries = _diaries.toMutableStateList()))
            }
            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.deleteDiary(diaryId = diaryId).onSuccess {}.onFailure {
                    if (it is KotlinNullPointerException) {
                        remove()
                        _diaries.clear()
                        postSideEffect(DiarySideEffect.DeleteSuccess)
                    }
                }
            }
        }
    }

    internal fun setContent(content: String) {
        setState(state.value.copy(content = content))
    }

    internal fun setTitle(title: String) {
        setState(state.value.copy(title = title))
    }

    internal fun setDate(date: String) {
        setState(state.value.copy(date = date))
    }

    internal fun setEmotion(emotion: Emotion) {
        setState(state.value.copy(emotion = emotion))
    }

    internal fun setDiaryId(diaryId: UUID) {
        setState(state.value.copy(diaryId = diaryId))
    }
}