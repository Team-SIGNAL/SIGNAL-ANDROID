package com.signal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.signal.data.dao.DiagnosisDao
import com.signal.data.model.diagnosis.DiagnosisModel

@Database(
    entities = [DiagnosisModel::class],
    version = 1,
)
abstract class SignalDatabase : RoomDatabase() {
    abstract fun getDiagnosisDao(): DiagnosisDao
}
