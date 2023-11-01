package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.ontrip

import com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip.RemoteOnTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.OnTripResponse

fun RemoteOnTripResponse.asDomain() = OnTripResponse(
    data.asDomain(),
    message,
    status
)