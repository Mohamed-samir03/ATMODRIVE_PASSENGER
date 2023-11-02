package com.mosamir.atmodrivepassenger.features.trip.domain.model

data class TripDetailsResponse(
    val `data`: TripDetailsData,
    val message: String,
    val status: Boolean
)