package com.sebrahimi.motiontagdemo.db.location

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebrahimi.motiontagdemo.db.ConstDB

@Database(
    entities = [
        LocationEntity::class
    ], version = ConstDB.DB_VERSION
)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDAO
}