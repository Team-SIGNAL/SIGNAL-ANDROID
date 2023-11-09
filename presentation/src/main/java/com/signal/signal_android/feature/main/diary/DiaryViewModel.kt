package com.signal.signal_android.feature.main.diary

import androidx.lifecycle.viewModelScope
import com.signal.domain.enums.Emotion
import com.signal.domain.repository.DiaryRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val diaryRepository: DiaryRepository,
): BaseViewModel<DiaryState, DiarySideEffect>(DiaryState.getDefaultState()) {
    internal fun createDiary() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                diaryRepository.createDiary(
                    title = title,
                    content = content,
                    emotion = emotion,
                    date = date,
                    image = image,
                )
            }
        }
    }

    internal fun setTitle(title: String) {
        setState(state.value.copy(title = title))
    }

    internal fun setContent(content: String) {
        setState(state.value.copy(content = content))
    }

    internal fun setEmotion(emotion: Emotion) {
        setState(state.value.copy(emotion = emotion))
    }
}