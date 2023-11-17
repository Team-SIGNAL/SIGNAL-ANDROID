package com.signal.data.model.reservation

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchHospitalsEntity

data class FetchHospitalsResponse(
    @SerializedName("hospital_list") val hospitals: List<Hospitals>

) {
    data class Hospitals(
        @SerializedName("image") val image: String,
        @SerializedName("name") val name: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("address") val address: String,
    )
}

fun FetchHospitalsResponse.toEntity() =
    FetchHospitalsEntity(hospitals = this.hospitals.map { it.toEntity() })

private fun FetchHospitalsResponse.Hospitals.toEntity() = FetchHospitalsEntity.HospitalsEntity(
    image = this.image,
    name = this.name,
    phone = this.phone,
    address = this.address,
)