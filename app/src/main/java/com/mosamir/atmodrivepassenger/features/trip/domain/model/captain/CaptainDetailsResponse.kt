package com.mosamir.atmodrivepassenger.features.trip.domain.model.captain

data class CaptainDetailsResponse(
    val `data`: CaptainData,
    val message: String,
    val status: Boolean
)