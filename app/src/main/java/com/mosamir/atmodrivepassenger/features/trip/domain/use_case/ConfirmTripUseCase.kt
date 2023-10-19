package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class ConfirmTripUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
):IConfirmTripUseCase {


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
        return iTripRepo.confirmTrip(vehicleClassId,
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


}