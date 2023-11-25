package com.signal.data.model.mypage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.signal.domain.entity.UserInformationEntity
import java.util.UUID

@Entity("userInformation")
data class UserInformationModel(
    @PrimaryKey val id: UUID,
    val name: String,
    val phone: String,
    val birth: String,
    val profile: String?,
)

fun UserInformationModel.toEntity() = UserInformationEntity(
    name = this.name,
    phone = this.phone,
    birth = this.birth,
    imageUrl = this.profile,
)

fun UserInformationEntity.toModel() = UserInformationModel(
    id = UUID.randomUUID(),
    name = this.name,
    phone = this.phone,
    birth = this.birth,
    profile = this.imageUrl,
)