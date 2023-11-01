package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.ontrip

import com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip.RemoteEndTripDetails
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.EndTripDetails

fun RemoteEndTripDetails.asDomain() = EndTripDetails(
    cost,
    id
)