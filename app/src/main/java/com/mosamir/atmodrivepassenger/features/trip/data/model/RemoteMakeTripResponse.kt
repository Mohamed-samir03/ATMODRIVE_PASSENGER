package com.mosamir.atmodrivepassenger.features.trip.data.model

data class RemoteMakeTripResponse(
    val `data`: RemoteMakeTripData?,
    val message: String,
    val status: Boolean
)