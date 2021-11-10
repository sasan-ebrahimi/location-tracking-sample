package com.sebrahimi.motiontagdemo.map

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.sebrahimi.motiontagdemo.R
import com.sebrahimi.motiontagdemo.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<MapViewModel>()

    private val TAG = "MapActivity"
    private lateinit var _map: GoogleMap
    private lateinit var _binding: ActivityMapsBinding

    @Inject
    lateinit var mapCircleOptions: CircleOptions

    @Inject
    lateinit var locationUpdateOptions: LocationUpdateOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observeLocationUpdates()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        _map = googleMap
        moveToLastLocation()
        collectDataFromDB()
    }

    @SuppressLint("MissingPermission")
    private fun moveToLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Log.d(TAG, "moveToLastLocation: $location")
            if (location != null) {
                val latlng = LatLng(location.latitude, location.longitude)
                _map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latlng,
                        16f
                    )
                )
                //mMap.addMarker(MarkerOptions().position(latlng).title("Your Last Location"))
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun observeLocationUpdates() {

        val listener = android.location.LocationListener { location ->
            _map.moveCamera(location.toCameraUpdate())
            GlobalScope.launch(Dispatchers.IO) {
                Log.d(TAG, "observeLocationUpdates: new location received: $location")
                viewModel.insertLocation(location)
            }
        }

        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            locationUpdateOptions.provider,
            locationUpdateOptions.minTimeMs,
            locationUpdateOptions.minDistanceM,
            listener
        )
    }

    private fun collectDataFromDB() {
        viewModel.fetchLocationsFromDB()
        lifecycleScope.launch {
            viewModel.newLocationsToShow.collect {
                Log.d(TAG, "collectDataFromDB: locations to draw on map")
                it.forEach { point ->
                    mapCircleOptions.center(point.location)
                    mapCircleOptions.clickable(true)
                    _map.addCircle(mapCircleOptions)
                }
            }
        }
    }

    private fun Location.toCameraUpdate(): CameraUpdate {
        return CameraUpdateFactory.newLatLng(LatLng(latitude,longitude))
    }

}