package com.signal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.signal.data.dao.DiagnosisDao
import com.signal.data.dao.MyPageDao
import com.signal.data.model.diagnosis.DiagnosisHistoryModel
import com.signal.data.model.diagnosis.DiagnosisModel
import com.signal.data.model.mypage.FamousSayingModel
import com.signal.data.model.mypage.UserInformationModel

@Database(
    entities = [
        DiagnosisModel::class,
        DiagnosisHistoryModel::class,
        FamousSayingModel::class,
        UserInformationModel::class,
    ],
    version = 1,
)
abstract class SignalDatabase : RoomDatabase() {
    abstract fun getDiagnosisDao(): DiagnosisDao
    abstract fun getMyPageDao(): MyPageDao
}
