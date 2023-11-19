package com.signal.data.model.mypage

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.signal.domain.entity.FamousSayingEntity

@Entity("famousSaying")
data class FamousSayingModel(
    @PrimaryKey val id: Long,
    val famousSaying: String,
)

fun FamousSayingModel.toEntity() = FamousSayingEntity(
    id = this.id,
    famousSaying = this.famousSaying,
)

fun FamousSayingEntity.toModel() = FamousSayingModel(
    id = this.id,
    famousSaying = this.famousSaying,
)