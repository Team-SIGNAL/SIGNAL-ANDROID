package com.signal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.signal.data.model.mypage.FamousSayingModel
import com.signal.data.model.mypage.UserInformationModel

@Dao
interface MyPageDao {
    @Insert
    suspend fun addFamousSaying(famousSayings: List<FamousSayingModel>)

    @Query("select * from famousSaying where id = :id")
    suspend fun getFamousSaying(id: Long): FamousSayingModel?

    @Insert
    suspend fun setUserInformation(userInformationModel: UserInformationModel)

    @Update
    suspend fun updateUserInformation(userInformationModel: UserInformationModel)

    @Query("select * from userInformation")
    suspend fun getUserInformation(): UserInformationModel
}