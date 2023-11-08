package com.signal.signal_android.feature.file

import java.io.File

data class FileState(
    val files: List<File>,
    val imageUrl: String,
) {
    companion object {
        fun getDefaultState() = FileState(
            files = emptyList(),
            imageUrl = "",
        )
    }
}