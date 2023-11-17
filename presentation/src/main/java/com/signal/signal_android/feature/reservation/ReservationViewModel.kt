package com.signal.signal_android.feature.reservation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.repository.ReservationRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val reservationRepository: ReservationRepository,
) : BaseViewModel<ReservationState, ReservationSideEffect>(ReservationState.getDefaultState()) {

    private val _dayReservations: MutableList<FetchDayReservationsEntity.DayReservationsEntity> =
        mutableListOf()
    private val _hospitals: MutableList<FetchHospitalsEntity.HospitalsEntity> = mutableListOf()

    internal fun fetchDayReservations() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    reservationRepository.fetchDayReservations(date = date)
                }.onSuccess {
                    _dayReservations.addAll(it.reservations)
                    setState(
                        copy(
                            dayReservationsEntity = _dayReservations,
                            isDayReservationsEmpty = _dayReservations.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isDayReservationsEmpty = _dayReservations.isEmpty()))
                }
            }
        }
    }

    internal fun fetchHospitals() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    reservationRepository.fetchHospitals()
                }.onSuccess {
                    _hospitals.addAll(it.hospitals)
                    setState(
                        copy(
                            hospitals = _hospitals,
                            isHospitalsEmpty = _hospitals.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isHospitalsEmpty = _hospitals.isEmpty()))
                }
            }
        }
    }

    internal fun createReservation() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    reservationRepository.createReservation(
                        reason = reason,
                        date = reservationDate,
                    )
                }.onSuccess {
                    ReservationSideEffect.CreateReservationSuccess
                }.onFailure {
                    when (it) {
                        is KotlinNullPointerException -> Log.d("TEST", "sadfadsfas")
                    }
                }
            }
        }
    }

    internal fun fetchReservationDetails() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    reservationRepository.fetchReservationDetails(
                        reservationId = reservationId,
                    )
                }.onSuccess {
                    setState(
                        copy(
                            image = image,
                            name = name,
                            address = address,
                            reservationStatus = reservationStatus,
                            date = date,
                            phone = phone,
                            reason = reason,
                        )
                    )
                }
            }
        }
    }

    internal fun setDate(date: String) {
        setState(state.value.copy(date = date))
    }

    internal fun setTime(time: String) {
        setState(state.value.copy(time = time))
    }

    internal fun setReason(reason: String) {
        setState(state.value.copy(reason = reason))
    }

    internal fun setReservationId(reservationId: Long) {
        setState(state.value.copy(reservationId = reservationId))
    }
}