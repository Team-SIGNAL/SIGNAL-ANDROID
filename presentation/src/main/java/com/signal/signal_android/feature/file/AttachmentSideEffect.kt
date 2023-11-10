package com.signal.signal_android.feature.file

interface AttachmentSideEffect {
    object Success: AttachmentSideEffect
    object Failure: AttachmentSideEffect
}