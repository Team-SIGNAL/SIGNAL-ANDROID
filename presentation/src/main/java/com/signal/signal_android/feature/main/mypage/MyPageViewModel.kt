package com.signal.signal_android.feature.main.mypage

import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.UserInformationEntity
import com.signal.domain.usecase.users.FetchUserInformationUseCase
import com.signal.domain.usecase.users.GetFamousSayingUseCase
import com.signal.domain.usecase.users.GetUserInformationUseCase
import com.signal.domain.usecase.users.SecessionUseCase
import com.signal.domain.usecase.users.SetUserInformationUseCase
import com.signal.domain.usecase.users.SignOutUseCase
import com.signal.domain.usecase.users.UpdateUserInformationUseCase
import com.signal.signal_android.BaseViewModel
import com.signal.signal_android.feature.mypage.MyPageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val secessionUseCase: SecessionUseCase,
    private val fetchUserInformationUseCase: FetchUserInformationUseCase,
    private val getFamousSayingUseCase: GetFamousSayingUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val setUserInformationUseCase: SetUserInformationUseCase,
    private val updateUserInformationUseCase: UpdateUserInformationUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState.getDefaultState()) {

    init {
        getFamousSaying()
        getUserInformation()
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
                setUserInformation(userInformationEntity = it)
                setUserInformationState(userInformationEntity = it)
            }
        }
    }

    private fun setUserInformationState(userInformationEntity: UserInformationEntity) {
        with(userInformationEntity) {
            setState(
                state.value.copy(
                    name = name,
                    phone = phone,
                    birth = birth,
                    profile = imageUrl,
                )
            )
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

    private fun getUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInformationUseCase().onSuccess {
                setUserInformationState(userInformationEntity = it)
            }.onFailure {
                fetchUserInformation()
            }
        }
    }

    private fun setUserInformation(userInformationEntity: UserInformationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            setUserInformationUseCase(userInformationEntity = userInformationEntity)
        }
    }

    // TODO: 유저 프로필 정보 수정 후 호출
    internal fun updateUserInformation(userInformationEntity: UserInformationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserInformationUseCase(userInformationEntity = userInformationEntity).onSuccess {
                getUserInformation()
            }
        }
    }
}