package com.signal.signal_android.feature.main.feed

sealed interface FeedSideEffect {
    object PostSuccess : FeedSideEffect
    object ClearFocus: FeedSideEffect
}
