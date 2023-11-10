package com.signal.signal_android.feature.file

import androidx.lifecycle.viewModelScope
import com.signal.domain.repository.AttachmentRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AttachmentViewModel(
    private val attachmentRepository: AttachmentRepository,
) : BaseViewModel<AttachmentState, AttachmentSideEffect>(AttachmentState.getDefaultState()) {
    internal fun uploadFile() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                attachmentRepository.uploadFile(files = files).onSuccess {
                    setState(copy(imageUrl = it.image))
                    postSideEffect(AttachmentSideEffect.Success)
                }.onFailure {
                    postSideEffect(AttachmentSideEffect.Failure)
                }
            }
        }
    }

    internal fun setFile(file: File) {
        setState(state.value.copy(files = listOf(file)))
    }
}