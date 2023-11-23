package com.signal.signal_android.feature.reservation

sealed interface ReservationSideEffect {
    object CreateReservationSuccess: ReservationSideEffect
}