package com.mosamir.atmodrivepassenger.features.trip.data.repository

import com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote.ITripDataSource
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class TripRepo @Inject constructor(
    private val iTripDataSource: ITripDataSource
):ITripRepo {


    override suspend fun makeTrip(
        distanceText: String,
        distanceValue: Long,
        durationText: String,
        durationValue: Long
    ): IResult<MakeTripResponse> {
        return iTripDataSource.makeTrip(distanceText, distanceValue, durationText, durationValue)
    }


}