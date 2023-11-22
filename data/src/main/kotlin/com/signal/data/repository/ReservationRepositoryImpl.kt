package com.signal.data.repository

import com.signal.data.datasource.reservation.ReservationDataSource
import com.signal.data.model.reservation.CreateReservationRequest
import com.signal.data.model.reservation.toEntity
import com.signal.domain.entity.FetchDayReservationsEntity
import com.signal.domain.entity.FetchHospitalsEntity
import com.signal.domain.entity.FetchReservationDetailsEntity
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
            CreateReservationRequest(
                reason = reason,
                date = date,
                time = time,
            )
        )
    }

    override suspend fun fetchDayReservations(date: String): FetchDayReservationsEntity =
        reservationDataSource.fetchDayReservations(date = date).toEntity()

    override suspend fun fetchHospitals(): FetchHospitalsEntity =
        reservationDataSource.fetchHospitals().toEntity()

    override suspend fun fetchReservationDetails(reservationId: UUID): FetchReservationDetailsEntity =
        reservationDataSource.fetchReservationDetails(reservationId = reservationId).toEntity()
}