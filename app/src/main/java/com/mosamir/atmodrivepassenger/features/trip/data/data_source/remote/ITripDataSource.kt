package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ITripDataSource {

    suspend fun makeTrip(distanceText: String,
                                   distanceValue: Long,
                                   durationText: String,
                                   durationValue: Long): IResult<MakeTripResponse>


    suspend fun confirmTrip(vehicleClassId: String,pickupLat: String,
                            pickupLng: String,dropOffLat: String,dropOffLng: String,
                            estimateCost: String,estimateTime: String,estimateDistance: String,
                            pickupLocationName: String,dropOffLocationName: String,
    ): IResult<ConfirmTripResponse>

    suspend fun cancelBeforeCaptain(tripId: Int): IResult<CancelTripResponse>

}