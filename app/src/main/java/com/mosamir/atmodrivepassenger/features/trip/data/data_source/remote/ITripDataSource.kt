package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ITripDataSource {

    suspend fun makeTrip(distanceText: String,
                                   distanceValue: Long,
                                   durationText: String,
                                   durationValue: Long): IResult<MakeTripResponse>

}