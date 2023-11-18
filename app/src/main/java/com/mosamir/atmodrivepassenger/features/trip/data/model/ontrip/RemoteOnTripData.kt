package com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip

data class RemoteOnTripData(
    val dropoff_lat: String,
    val dropoff_lng: String,
    val dropoff_location_name: String,
    val pickup_lat: String,
    val pickup_lng: String,
    val pickup_location_name: String,
    val start_date: Any?,
    val trip_id: Int,
    val trip_status: String,
    val car_brand : String,
    val car_model: String
)