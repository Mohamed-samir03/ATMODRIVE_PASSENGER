package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class GetTripDetailsUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
):IGetTripDetailsUseCase {
    override suspend fun getTripDetails(tripId: Int): IResult<TripDetailsResponse> {
        return iTripRepo.getTripDetails(tripId)
    }
}