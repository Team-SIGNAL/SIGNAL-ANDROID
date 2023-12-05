package com.signal.signal_android.feature.bug

import androidx.lifecycle.viewModelScope
import com.signal.domain.repository.ReportRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class BugViewModel(
    private val reportRepository: ReportRepository,
) : BaseViewModel<BugState, BugSideEffect>(BugState.getDefaultState()) {

    internal fun reportBug() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                reportRepository.reportBug(
                    content = content,
                    image = image,
                ).onSuccess {
                    postSideEffect(BugSideEffect.Success)
                }.onFailure {

                }
            }
        }
    }

    internal fun setContent(content: String) {
        setState(state.value.copy(content = content))
    }

    internal fun setImage(image: String){
        setState(state.value.copy(image = image))
    }
}