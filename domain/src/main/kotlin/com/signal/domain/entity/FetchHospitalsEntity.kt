package com.signal.domain.entity

import java.util.UUID

data class FetchHospitalsEntity(
    val hospitals: List<HospitalEntity>
) {
    data class HospitalEntity(
        val id: UUID,
        val profile: String?,
        val name: String,
        val phone: String,
        val address: String,
    )
}
