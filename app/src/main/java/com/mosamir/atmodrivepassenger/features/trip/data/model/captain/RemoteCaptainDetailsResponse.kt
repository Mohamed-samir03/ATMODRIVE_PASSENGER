package com.mosamir.atmodrivepassenger.features.trip.data.model.captain

data class RemoteCaptainDetailsResponse(
    val `data`: RemoteCaptainData,
    val message: String,
    val status: Boolean
)