package com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip

data class OnTripResponse(
    val `data`: OnTripData,
    val message: String,
    val status: Boolean
)