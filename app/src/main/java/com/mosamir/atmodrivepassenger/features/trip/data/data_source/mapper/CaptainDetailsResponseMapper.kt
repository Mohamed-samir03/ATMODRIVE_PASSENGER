package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.captain.RemoteCaptainDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse

fun RemoteCaptainDetailsResponse.asDomain() = CaptainDetailsResponse(
    data.asDomain(),
    message,
    status
)