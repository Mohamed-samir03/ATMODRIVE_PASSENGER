package com.mosamir.atmodrivepassenger.features.trip.domain.repository

import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ITripRepo {


    suspend fun makeTrip(distanceText: String,
                         distanceValue: Long,
                         durationText: String,
                         durationValue: Long): IResult<MakeTripResponse>

}