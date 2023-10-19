package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IMakeTripUseCase {

    suspend fun makeTrip(distanceText: String,
                         distanceValue: Long,
                         durationText: String,
                         durationValue: Long): IResult<MakeTripResponse>

}