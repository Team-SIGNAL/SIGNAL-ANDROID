package com.signal.signal_android.feature.file

import java.io.File

data class AttachmentState(
    val files: List<File>,
    val imageUrl: String,
) {
    companion object {
        fun getDefaultState() = AttachmentState(
            files = emptyList(),
            imageUrl = "",
        )
    }
}