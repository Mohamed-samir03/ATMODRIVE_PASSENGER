package com.mosamir.atmodrivepassenger.features.trip.data.repository

import com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote.ITripDataSource
import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse
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

    override suspend fun confirmTrip(
        vehicleClassId: String,
        pickupLat: String,
        pickupLng: String,
        dropOffLat: String,
        dropOffLng: String,
        estimateCost: String,
        estimateTime: String,
        estimateDistance: String,
        pickupLocationName: String,
        dropOffLocationName: String
    ): IResult<ConfirmTripResponse> {
        return iTripDataSource.confirmTrip(vehicleClassId,
            pickupLat,
            pickupLng,
            dropOffLat,
            dropOffLng,
            estimateCost,
            estimateTime,
            estimateDistance,
            pickupLocationName,
            dropOffLocationName)
    }

    override suspend fun cancelBeforeCaptain(tripId: Int): IResult<CancelTripResponse> {
        return iTripDataSource.cancelBeforeCaptain(tripId)
    }

    override suspend fun getCaptainDetails(tripId: Int): IResult<CaptainDetailsResponse> {
        return iTripDataSource.getCaptainDetails(tripId)
    }


}