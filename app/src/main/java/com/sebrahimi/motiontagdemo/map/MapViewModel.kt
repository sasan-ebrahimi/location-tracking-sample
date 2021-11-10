package com.sebrahimi.motiontagdemo.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebrahimi.motiontagdemo.repo.LocationRepo
import com.sebrahimi.motiontagdemo.db.location.LocationEntity
import com.sebrahimi.motiontagdemo.db.location.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepo: LocationRepo
) : ViewModel() {

    private val _newLocationsToShow = MutableStateFlow(listOf<LocationModel>())
    val newLocationsToShow: StateFlow<List<LocationModel>> = _newLocationsToShow

    private val TAG = "MapViewModel"

    fun fetchLocationsFromDB() {
        viewModelScope.launch {
            locationRepo.getLocations().collect {
                _newLocationsToShow.emit(trimToNewLocations(it))
            }
        }
    }

    /**
     * Removes the location objects that previously exist in #newLocationsToShow.
     * This helps to prevent unnecessary draws on the map.
     * @param list the list of last locations fetched from database
     * @return a list of LocationModels that have not been drawn on the map
     */
    private fun trimToNewLocations(list: List<LocationModel>): List<LocationModel> {
        if(_newLocationsToShow.value.isEmpty())
            return list
        Log.d(TAG, "trimToNewLocations: input list $list")
        val result = mutableListOf<LocationModel>()
        val lastDrawnLocationId = _newLocationsToShow.value.first().id
        for (item in list){
            if(item.id == lastDrawnLocationId)
                break
            result.add(result.size , item)
        }
        Log.d(TAG, "trimToNewLocations: output list: $result")
        return result
    }

    fun insertLocation(location: Location) {
        locationRepo.insertLocation(
            LocationEntity.Builder()
                .setLocation(location)
                .build()
        )
    }
}