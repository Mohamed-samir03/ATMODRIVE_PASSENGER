package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripData

class SharedViewModel : ViewModel() {

    val location = MutableLiveData<String>()
    val locType = MutableLiveData<String>()
    val makeTripData = MutableLiveData<MakeTripData>()


    fun setLocation(loc: String) {
        location.value = loc
    }

    fun setLocType(type: String){
        locType.value = type
    }

    fun setMakeTripData(trip:MakeTripData){
        makeTripData.value = trip
    }

}