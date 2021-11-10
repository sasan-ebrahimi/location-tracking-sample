package com.sebrahimi.motiontagdemo.map

import android.graphics.Color
import android.location.LocationManager
import com.google.android.gms.maps.model.CircleOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MapModule {

    private const val CIRCLE_FILL_COLOR = "#88FF0000"
    private const val CIRCLE_STROKE_COLOR = "#FFFF0000"
    private const val LOCATION_UPDATE_MIN_TIME = 4000L
    private const val LOCATION_UPDATE_MIN_DISTANCE = 5f

    @Provides
    fun provideCircleOption(): CircleOptions {
        val options = CircleOptions()
        options.fillColor(Color.parseColor(CIRCLE_FILL_COLOR))
        options.strokeColor(Color.parseColor(CIRCLE_STROKE_COLOR))
        options.radius(8.0)
        options.visible(true)
        return options
    }

    @Provides
    fun provideLocationUpdateOptions(): LocationUpdateOptions {
        return LocationUpdateOptions(
            LocationManager.GPS_PROVIDER,
            LOCATION_UPDATE_MIN_TIME,
            LOCATION_UPDATE_MIN_DISTANCE
        )
    }

}