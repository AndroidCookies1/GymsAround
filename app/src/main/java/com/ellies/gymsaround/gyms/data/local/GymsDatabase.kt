package com.ellies.gymsaround.gyms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 3,
    exportSchema = false,
)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDao
}