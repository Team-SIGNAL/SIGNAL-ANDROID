package com.signal.signal_android.feature.file

import androidx.lifecycle.viewModelScope
import com.signal.domain.repository.FileRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FileViewModel(
    private val fileRepository: FileRepository,
) : BaseViewModel<FileState, FileSideEffect>(FileState.getDefaultState()) {
    internal fun uploadFile() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                fileRepository.uploadFile(files = files).onSuccess {
                    setState(copy(imageUrl = it.image))
                }.onFailure {
                    postSideEffect(FileSideEffect.Failure)
                }
            }
        }
    }
}