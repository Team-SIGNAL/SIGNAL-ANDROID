package com.signal.signal_android.feature.mypage

data class MyPageState(
    val name: String,
    val phone: String,
    val birth: String,
    val profile: String?,
    val famousSaying: String,
) {
    companion object {
        fun getDefaultState() = MyPageState(
            name = "",
            phone = "",
            birth = "",
            profile = "https://github.com/Team-SIGNAL/SIGNAL-ANDROID/blob/develop/presentation/src/main/res/drawable/ic_profile_image.png?raw=true",
            famousSaying = "",
        )
    }
}