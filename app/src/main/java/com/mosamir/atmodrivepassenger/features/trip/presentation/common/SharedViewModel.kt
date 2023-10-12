package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val location = MutableLiveData<String>()
    val locType = MutableLiveData<String>()


    fun setLocation(loc: String) {
        location.value = loc
    }

    fun setLocType(type: String){
        locType.value = type
    }

}