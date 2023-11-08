package com.signal.signal_android.feature.file

interface FileSideEffect {
    object Success: FileSideEffect
    object Failure: FileSideEffect
}