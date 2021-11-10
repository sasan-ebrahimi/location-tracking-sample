package com.sebrahimi.motiontagdemo.repo

import android.util.Log
import com.sebrahimi.motiontagdemo.db.location.LocationDatabase
import com.sebrahimi.motiontagdemo.db.location.LocationEntity
import com.sebrahimi.motiontagdemo.db.location.LocationModel
import com.sebrahimi.motiontagdemo.db.location.mapToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepo @Inject constructor(private val locationDB: LocationDatabase) {

    private val TAG = "LocationRepo"

    fun insertLocation(location: LocationEntity): Long {
        return locationDB.locationDao().insertLocation(location)
    }

    /**
     * Fetch the most recent list of locations from database
     * @param count the number of rows to be fetched from database
     * @return A Flow object wrapping the list of fetched data
     */
    fun getLocations(count : Int = 100): Flow<List<LocationModel>> {
        Log.d(TAG, "getLocations: requested $count rows from DB")
        return locationDB.locationDao().getLocations(count).map {list->
            Log.d(TAG, "getLocations: fetched list from DB: $list")
            list.map {entity ->
                entity.mapToModel()
            }
        }
    }
}