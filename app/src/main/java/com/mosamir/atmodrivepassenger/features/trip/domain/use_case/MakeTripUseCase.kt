package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class MakeTripUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
):IMakeTripUseCase {
    override suspend fun makeTrip(
        distanceText: String,
        distanceValue: Long,
        durationText: String,
        durationValue: Long
    ): IResult<MakeTripResponse> {
        return iTripRepo.makeTrip(distanceText, distanceValue, durationText, durationValue)
    }
}