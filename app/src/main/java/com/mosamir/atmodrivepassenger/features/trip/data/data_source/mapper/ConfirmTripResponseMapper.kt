package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse

fun RemoteConfirmTripResponse.asDomain() = ConfirmTripResponse(
    message,
    status,
    trip_id
)