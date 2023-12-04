package com.signal.signal_android.feature.bug

internal data class BugState(
    val content: String,
    val image: String,
) {
    companion object {
        fun getDefaultState() = BugState(
            content = "",
            image = "",
        )
    }
}