package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.MonthDiaryEntity
import java.time.LocalDateTime

data class FetchMonthDiariesResponse(
    @SerializedName("month_diary_list") val monthDiaries: List<Diary>,
) {
    data class Diary(
        @SerializedName("id") val id: Long,
        @SerializedName("date") val date: LocalDateTime,
    )
}

fun FetchMonthDiariesResponse.toEntity() = MonthDiaryEntity(
    monthDiaryEntity = this.monthDiaries.map { it.toEntity() },
)

private fun FetchMonthDiariesResponse.Diary.toEntity() = MonthDiaryEntity.MonthDiaryEntity(
    id = this.id,
    date = this.date,
)