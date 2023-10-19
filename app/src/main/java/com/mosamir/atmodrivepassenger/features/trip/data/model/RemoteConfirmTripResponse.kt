package com.mosamir.atmodrivepassenger.features.trip.data.model

data class RemoteConfirmTripResponse(
    val message: String,
    val status: Boolean,
    val trip_id: Int?
)