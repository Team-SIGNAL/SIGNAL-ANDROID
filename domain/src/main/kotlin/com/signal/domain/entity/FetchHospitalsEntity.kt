package com.signal.domain.entity

data class FetchHospitalsEntity(
    val hospitals: List<HospitalsEntity>
) {
    data class HospitalsEntity(
        val image: String?,
        val name: String,
        val phone: String,
        val address: String,
    )
}
