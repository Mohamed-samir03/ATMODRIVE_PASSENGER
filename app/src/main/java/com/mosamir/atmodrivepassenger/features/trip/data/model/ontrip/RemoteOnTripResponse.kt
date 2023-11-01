package com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip

data class RemoteOnTripResponse(
    val `data`: RemoteOnTripData,
    val message: String,
    val status: Boolean
)