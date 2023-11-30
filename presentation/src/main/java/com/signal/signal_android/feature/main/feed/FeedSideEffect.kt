package com.signal.signal_android.feature.main.feed

sealed interface FeedSideEffect {
    object PostSuccess : FeedSideEffect
    object DeleteSuccess: FeedSideEffect
    object ClearFocus: FeedSideEffect
    object CommentSuccess: FeedSideEffect
}
