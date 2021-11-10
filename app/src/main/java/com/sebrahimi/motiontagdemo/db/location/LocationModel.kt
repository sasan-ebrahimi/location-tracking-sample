package com.sebrahimi.motiontagdemo.db.location

import com.google.android.gms.maps.model.LatLng

data class LocationModel(
    val id: Long,
    val location: LatLng,
    val mode: Int,
    val timestamp: Long
)