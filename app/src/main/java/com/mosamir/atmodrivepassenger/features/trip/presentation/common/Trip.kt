package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Trip(
    val captainId:Int,
    val cost:Double,
    val distance:Double,
    val lat:String,
    val lng:String,
    val passengerId:Int,
    val status:String,
    val tripId:Int,
    val waitTime:Double
){
    constructor():this(0,0.0,0.0,"","",0,"",0,0.0)
}
