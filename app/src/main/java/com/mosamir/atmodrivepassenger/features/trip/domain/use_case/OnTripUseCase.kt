package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.OnTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class OnTripUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
):IOnTripUseCase{
    override suspend fun onTrip(): IResult<OnTripResponse> {
        return iTripRepo.onTrip()
    }
}