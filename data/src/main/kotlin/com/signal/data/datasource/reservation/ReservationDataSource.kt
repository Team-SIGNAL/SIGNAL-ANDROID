package com.signal.data.datasource.reservation

import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.FetchDayReservationsResponse
import com.signal.data.model.reservation.FetchHospitalsResponse
import com.signal.data.model.reservation.FetchReservationDetailsResponse
import java.util.UUID

interface ReservationDataSource {
    suspend fun createReservation(
        hospitalId: UUID,
        createReservationRequest: CreateReservationRequest
    )
    suspend fun fetchDayReservations(date: String): FetchDayReservationsResponse
    suspend fun fetchHospitals(): FetchHospitalsResponse
    suspend fun fetchReservationDetails(reservationId: UUID): FetchReservationDetailsResponse
}