package com.signal.signal_android.feature.bug

sealed interface BugSideEffect {
    object Success: BugSideEffect
}