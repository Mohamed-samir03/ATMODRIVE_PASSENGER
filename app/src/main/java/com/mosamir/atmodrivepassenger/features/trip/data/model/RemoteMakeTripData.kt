package com.mosamir.atmodrivepassenger.features.trip.data.model

data class RemoteMakeTripData(
    val estimate_cost: String,
    val estimate_distance: Double,
    val estimate_time: Int,
    val vehicle_class_icon: String,
    val vehicle_class_name: String
)