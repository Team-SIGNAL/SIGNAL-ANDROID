package com.signal.signal_android.feature.mypage

import java.time.LocalDate

data class MyPageState(
    val name: String,
    val phoneNumber: String,
    val birth: LocalDate,
    val profile: String,
) {
    companion object {
        fun getDefaultState() = MyPageState(
            name = "",
            phoneNumber = "",
            birth = LocalDate.now(),
            profile = "https://github.com/Team-SIGNAL/SIGNAL-ANDROID/blob/develop/presentation/src/main/res/drawable/ic_profile_image.png?raw=true"
        )
    }
}