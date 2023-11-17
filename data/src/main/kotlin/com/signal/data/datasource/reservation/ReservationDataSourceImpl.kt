package com.signal.data.datasource.reservation

import com.signal.data.api.ReservationApi
import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.FetchDayReservationsResponse
import com.signal.data.model.reservation.FetchHospitalsResponse
import com.signal.data.model.reservation.FetchReservationDetailsResponse
import com.signal.data.util.ExceptionHandler

class ReservationDataSourceImpl(
    private val reservationApi: ReservationApi,
) : ReservationDataSource {
    override suspend fun createReservation(createReservationRequest: CreateReservationRequest) =
        ExceptionHandler<Unit>().httpRequest {
            reservationApi.createReservation(createReservationRequest = createReservationRequest)
        }.sendRequest()

    override suspend fun fetchDayReservations(date: String) =
        ExceptionHandler<FetchDayReservationsResponse>().httpRequest {
            reservationApi.fetchDayReservations(date = date)
        }.sendRequest()

    override suspend fun fetchHospitals() = ExceptionHandler<FetchHospitalsResponse>().httpRequest {
        reservationApi.fetchHospitals()
    }.sendRequest()

    override suspend fun fetchReservationDetails(reservationId: Long) =
        ExceptionHandler<FetchReservationDetailsResponse>().httpRequest {
            reservationApi.fetchReservationDetails(reservationId = reservationId)
        }.sendRequest()
}