package com.signal.signal_android.feature.file

import java.io.File

data class AttachmentState(
    val image: File,
    val imageUrl: String,
) {
    companion object {
        fun getDefaultState() = AttachmentState(
            image = File(""),
            imageUrl = "",
        )
    }
}