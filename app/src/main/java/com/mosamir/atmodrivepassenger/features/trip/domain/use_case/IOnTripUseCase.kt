package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.OnTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IOnTripUseCase {
    suspend fun onTrip(): IResult<OnTripResponse>
}