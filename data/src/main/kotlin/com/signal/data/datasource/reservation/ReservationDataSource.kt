package com.signal.data.datasource.reservation

import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.FetchDayReservationsResponse
import com.signal.data.model.reservation.FetchHospitalsResponse
import com.signal.data.model.reservation.FetchReservationDetailsResponse

interface ReservationDataSource {
    suspend fun createReservation(createReservationRequest: CreateReservationRequest)
    suspend fun fetchDayReservations(date: String): FetchDayReservationsResponse
    suspend fun fetchHospitals(): FetchHospitalsResponse
    suspend fun fetchReservationDetails(reservationId: Long): FetchReservationDetailsResponse
}