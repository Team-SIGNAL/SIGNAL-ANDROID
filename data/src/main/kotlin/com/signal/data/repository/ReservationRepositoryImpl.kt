package com.signal.data.repository

import com.signal.data.datasource.reservation.ReservationDataSource
import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.toEntity
import com.signal.domain.repository.ReservationRepository
import java.util.UUID

class ReservationRepositoryImpl(
    private val reservationDataSource: ReservationDataSource,
) : ReservationRepository {
    override suspend fun createReservation(
        hospitalId: UUID,
        reason: String,
        date: String,
        time: String,
    ) = runCatching {
        reservationDataSource.createReservation(
            hospitalId = hospitalId,
            createReservationRequest = CreateReservationRequest(
                reason = reason,
                date = date,
                time = time,
            ),
        )
    }

    override suspend fun fetchDayReservations(date: String) =
        reservationDataSource.fetchDayReservations(date = date).toEntity()

    override suspend fun fetchHospitals() = reservationDataSource.fetchHospitals().toEntity()

    override suspend fun fetchReservationDetails(reservationId: UUID) =
        reservationDataSource.fetchReservationDetails(reservationId = reservationId).toEntity()
}