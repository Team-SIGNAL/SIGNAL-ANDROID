package com.signal.domain.repository

import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.entity.FetchReservationDetailsEntity

interface ReservationRepository {
    suspend fun createReservation(
        reason: String,
        date: String,
    ): Result<Unit>

    suspend fun fetchDayReservations(date: String): FetchDayReservationsEntity
    suspend fun fetchHospitals(): FetchHospitalsEntity
    suspend fun fetchReservationDetails(reservationId: Long): FetchReservationDetailsEntity
}