package com.signal.domain.entity

import java.util.UUID

data class FetchHospitalsEntity(
    val hospitals: List<HospitalsEntity>
) {
    data class HospitalsEntity(
        val id: UUID,
        val profile: String?,
        val name: String,
        val phone: String,
        val address: String,
    )
}
