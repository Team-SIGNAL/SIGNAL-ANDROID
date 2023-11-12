package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchMonthDiaryEntity
import java.time.LocalDateTime

data class FetchMonthDiaryResponse(
    @SerializedName("month_diary_list") val monthDiaryList: List<Diaries>,
) {
    data class Diaries(
        @SerializedName("id") val id: Long,
        @SerializedName("date") val date: LocalDateTime,
    )
}

fun FetchMonthDiaryResponse.toEntity() = FetchMonthDiaryEntity(
    monthDiaryEntity = this.monthDiaryList.map { it.toEntity() },
)

private fun FetchMonthDiaryResponse.Diaries.toEntity() = FetchMonthDiaryEntity.MonthDiaryEntity(
    id = this.id,
    date = this.date,
)