package com.signal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.signal.data.model.mypage.FamousSayingModel

@Dao
interface MyPageDao {
    @Insert
    suspend fun addFamousSaying(famousSayings: List<FamousSayingModel>)

    @Query("select * from famousSaying where id = :id")
    suspend fun getFamousSaying(id: Long): FamousSayingModel?
}