package com.signal.data.api

import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.FetchDayReservationsResponse
import com.signal.data.model.reservation.FetchHospitalsResponse
import com.signal.data.model.reservation.FetchReservationDetailsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservationApi {
    @GET(SignalUrl.Reservation.FetchHospitals)
    suspend fun fetchHospitals(): FetchHospitalsResponse

    @GET(SignalUrl.Reservation.CreateReservation)
    suspend fun createReservation(
        @Body createReservationRequest: CreateReservationRequest,
    )

    @GET(SignalUrl.Reservation.CreateReservation)
    suspend fun fetchDayReservations(
        @Query("date") date: String,
    ): FetchDayReservationsResponse

    @GET(SignalUrl.Reservation.FetchReservationDetails)
    suspend fun fetchReservationDetails(
        @Path("reservation_id") reservationId: Long,
    ): FetchReservationDetailsResponse
}