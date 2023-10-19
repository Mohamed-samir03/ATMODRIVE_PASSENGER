package com.mosamir.atmodrivepassenger.features.trip.domain.model

data class MakeTripResponse(
    val `data`: MakeTripData?,
    val message: String,
    val status: Boolean
)