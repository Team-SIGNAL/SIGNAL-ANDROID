package com.signal.signal_android.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    protected fun setState(state: S) {
        _state.tryEmit(state)
    }

    protected fun postSideEffect(sideEffect: E) {
        _sideEffect.tryEmit(sideEffect)
    }
}
