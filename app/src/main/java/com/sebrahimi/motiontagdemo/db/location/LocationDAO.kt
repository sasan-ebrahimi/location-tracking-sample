package com.sebrahimi.motiontagdemo.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sebrahimi.motiontagdemo.db.ConstDB
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {
    @Insert
    fun insertLocation(locationEntity: LocationEntity): Long

    @Query("SELECT * FROM ${ConstDB.TBL_LOCATION} ORDER BY timestamp DESC LIMIT :count")
    fun getLocations(count: Int): Flow<List<LocationEntity>>
}