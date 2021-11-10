package com.sebrahimi.motiontagdemo.map

data class LocationUpdateOptions(
    val provider: String,
    val minTimeMs : Long,
    val minDistanceM : Float
)
