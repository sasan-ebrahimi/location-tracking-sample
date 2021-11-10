package com.sebrahimi.motiontagdemo.db.location

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.maps.model.LatLng
import com.sebrahimi.motiontagdemo.db.ConstDB

@Entity(tableName = ConstDB.TBL_LOCATION)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val lat: Float,
    val lon: Float,
    val mode: Int?,
    val timestamp: Long
){
    class Builder {
        private var id =0L
        private var lat = 0f
        private var lon = 0f
        private var mode : Int? = null
        private var timestamp = System.currentTimeMillis()/1000

        fun setLocation(location: Location): Builder {
            this.lat = location.latitude.toFloat()
            this.lon = location.longitude.toFloat()
            return this
        }

        fun setMode(mode: Int?){
            this.mode = mode
        }

        fun setTimeStamp(timestamp: Long){
            this.timestamp = timestamp
        }

        fun build(): LocationEntity {
            return LocationEntity(
                id,
                lat,
                lon,
                mode,
                timestamp
            )
        }
    }
}

fun LocationEntity.mapToModel(): LocationModel {
    return LocationModel(
        id,
        LatLng(lat.toDouble(), lon.toDouble()),
        mode ?: DetectedActivity.UNKNOWN,
        timestamp
    )
}