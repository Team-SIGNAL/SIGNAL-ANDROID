package com.signal.domain.repository

import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.entity.FetchReservationDetailsEntity
import java.util.UUID

interface ReservationRepository {
    suspend fun createReservation(
        hospitalId: UUID,
        reason: String,
        date: String,
        time: String,
    ): Result<Unit>

    suspend fun fetchDayReservations(date: String): FetchDayReservationsEntity
    suspend fun fetchHospitals(): FetchHospitalsEntity
    suspend fun fetchReservationDetails(reservationId: UUID): FetchReservationDetailsEntity
}