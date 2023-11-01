package com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip

data class OnTripData(
    val captain_details: OnTripCaptainDetails,
    val dropoff_lat: String,
    val dropoff_lng: String,
    val dropoff_location_name: String,
    val end_trip_details: EndTripDetails,
    val pickup_lat: String,
    val pickup_lng: String,
    val pickup_location_name: String,
    val real_trip: Any?,
    val start_date: Int,
    val trip_id: Int,
    val trip_status: String
)