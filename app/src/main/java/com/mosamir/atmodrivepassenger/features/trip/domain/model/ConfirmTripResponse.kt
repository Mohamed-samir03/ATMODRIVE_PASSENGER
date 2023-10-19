package com.mosamir.atmodrivepassenger.features.trip.domain.model

data class ConfirmTripResponse(
    val message: String,
    val status: Boolean,
    val trip_id: Int?
)