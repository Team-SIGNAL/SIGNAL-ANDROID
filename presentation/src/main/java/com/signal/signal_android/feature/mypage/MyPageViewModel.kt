package com.signal.signal_android.feature.mypage

import androidx.lifecycle.viewModelScope
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.GetFamousSayingUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.signal_android.BaseViewModel
import com.signal.signal_android.feature.main.mypage.MyPageSideEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val secessionUseCase: SecessionUseCase,
    private val fetchUserInformationUseCase: FetchUserInformationUseCase,
    private val getFamousSayingUseCase: GetFamousSayingUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState.getDefaultState()) {

    init {
        fetchUserInformation()
        getFamousSaying()
    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SignOutSuccess)
            }
        }
    }

    internal fun secession() {
        viewModelScope.launch(Dispatchers.IO) {
            secessionUseCase().onFailure {
                when (it) {
                    is KotlinNullPointerException -> {
                        postSideEffect(MyPageSideEffect.SecessionSuccess)
                    }
                }
            }
        }
    }

    private fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUserInformationUseCase().onSuccess {
                setState(
                    state = state.value.copy(
                        name = it.name,
                        phone = it.phone,
                        birth = it.birth,
                        profile = it.imageUrl,
                    )
                )
            }.onFailure {

            }
        }
    }

    private fun getFamousSaying() {
        val random = 1..10
        viewModelScope.launch(Dispatchers.IO) {
            getFamousSayingUseCase(id = random.random().toLong()).onSuccess {
                setState(state.value.copy(famousSaying = it?.famousSaying ?: ""))
            }
        }
    }
}