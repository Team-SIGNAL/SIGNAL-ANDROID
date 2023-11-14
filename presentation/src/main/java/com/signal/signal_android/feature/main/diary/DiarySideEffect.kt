package com.signal.signal_android.feature.main.diary

sealed interface DiarySideEffect {
    object CreateDiarySuccess: DiarySideEffect
}