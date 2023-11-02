package com.mosamir.atmodrivepassenger.features.trip.data.model

data class RemoteTripDetailsResponse(
    val `data`: RemoteTripDetailsData,
    val message: String,
    val status: Boolean
)