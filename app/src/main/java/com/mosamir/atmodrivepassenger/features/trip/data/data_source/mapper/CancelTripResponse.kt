package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteCancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse

fun RemoteCancelTripResponse.asDomain() = CancelTripResponse(
    message,
    status
)