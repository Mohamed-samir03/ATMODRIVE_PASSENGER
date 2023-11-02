package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteTripDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsResponse

fun RemoteTripDetailsResponse.asDomain()  = TripDetailsResponse(
    data.asDomain(),
    message,
    status
)