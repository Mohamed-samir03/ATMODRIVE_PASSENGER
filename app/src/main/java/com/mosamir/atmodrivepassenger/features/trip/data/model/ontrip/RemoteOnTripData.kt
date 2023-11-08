package com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip

data class RemoteOnTripData(
    val captain_details: RemoteOnTripCaptainDetails,
    val dropoff_lat: String,
    val dropoff_lng: String,
    val dropoff_location_name: String,
    val end_trip_details: RemoteEndTripDetails,
    val pickup_lat: String,
    val pickup_lng: String,
    val pickup_location_name: String,
    val real_trip: Any?,
    val start_date: Any?,
    val trip_id: Int,
    val trip_status: String
)