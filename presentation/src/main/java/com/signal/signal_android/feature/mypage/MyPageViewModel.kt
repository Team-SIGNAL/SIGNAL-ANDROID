package com.signal.signal_android.feature.mypage

import androidx.lifecycle.viewModelScope
import com.signal.domain.usecase.users.FetchUserInfoUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MyPageViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val fetchUserInfoUseCase: FetchUserInfoUseCase,
    initialState: MyPageState,
) : BaseViewModel<MyPageState, MyPageSideEffect>(initialState) {

    private fun fetchUserInfo(
        name: String,
        phoneNumber: String,
        birth: LocalDate,
        profile: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUserInfoUseCase().onSuccess {
                setState(
                    state.value.copy(
                        name = name,
                        phoneNumber = phoneNumber,
                        birth = birth,
                        profile = profile,
                    ),
                )
            }
        }
    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.Success)
            }
        }
    }
}