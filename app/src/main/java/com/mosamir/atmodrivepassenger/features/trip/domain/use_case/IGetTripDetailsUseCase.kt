package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IGetTripDetailsUseCase {

    suspend fun getTripDetails(tripId: Int): IResult<TripDetailsResponse>

}