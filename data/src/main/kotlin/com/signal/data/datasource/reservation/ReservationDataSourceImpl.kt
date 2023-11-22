package com.signal.data.datasource.reservation

import com.signal.data.api.ReservationApi
import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.FetchDayReservationsResponse
import com.signal.data.model.reservation.FetchHospitalsResponse
import com.signal.data.model.reservation.FetchReservationDetailsResponse
import com.signal.data.util.ExceptionHandler
import java.util.UUID

class ReservationDataSourceImpl(
    private val reservationApi: ReservationApi,
) : ReservationDataSource {
    override suspend fun createReservation(
        hospitalId: UUID,
        createReservationRequest: CreateReservationRequest,
    ) = ExceptionHandler<Unit>().httpRequest {
        reservationApi.createReservation(
            hospitalId = hospitalId,
            createReservationRequest = createReservationRequest,
        )
    }.sendRequest()

    override suspend fun fetchDayReservations(date: String) =
        ExceptionHandler<FetchDayReservationsResponse>().httpRequest {
            reservationApi.fetchDayReservations(date = date)
        }.sendRequest()

    override suspend fun fetchHospitals() = ExceptionHandler<FetchHospitalsResponse>().httpRequest {
        reservationApi.fetchHospitals()
    }.sendRequest()

    override suspend fun fetchReservationDetails(reservationId: UUID) =
        ExceptionHandler<FetchReservationDetailsResponse>().httpRequest {
            reservationApi.fetchReservationDetails(reservationId = reservationId)
        }.sendRequest()
}