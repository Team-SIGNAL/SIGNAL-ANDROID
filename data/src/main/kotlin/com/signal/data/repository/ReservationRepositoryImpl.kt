package com.signal.data.repository

import com.signal.data.datasource.reservation.ReservationDataSource
import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.toEntity
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.entity.FetchReservationDetailsEntity
import com.signal.domain.repository.ReservationRepository

class ReservationRepositoryImpl(
    private val reservationDataSource: ReservationDataSource,
) : ReservationRepository {
    override suspend fun createReservation(
        reason: String,
        date: String,
    ) = runCatching {
        reservationDataSource.createReservation(
            CreateReservationRequest(
                reason = reason,
                date = date,
            )
        )
    }

    override suspend fun fetchDayReservations(date: String): FetchDayReservationsEntity =
        reservationDataSource.fetchDayReservations(date = date).toEntity()

    override suspend fun fetchHospitals(): FetchHospitalsEntity =
        reservationDataSource.fetchHospitals().toEntity()

    override suspend fun fetchReservationDetails(reservationId: Long): FetchReservationDetailsEntity =
        reservationDataSource.fetchReservationDetails(reservationId = reservationId).toEntity()
}