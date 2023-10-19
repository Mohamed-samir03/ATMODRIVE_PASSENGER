package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteMakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse

fun RemoteMakeTripResponse.asDomain() = MakeTripResponse(
    data?.asDomain(),
    message,
    status
)