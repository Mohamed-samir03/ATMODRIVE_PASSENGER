package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IConfirmTripUseCase {

    suspend fun confirmTrip(vehicleClassId: String,pickupLat: String,
                            pickupLng: String,dropOffLat: String,dropOffLng: String,
                            estimateCost: String,estimateTime: String,estimateDistance: String,
                            pickupLocationName: String,dropOffLocationName: String,
    ): IResult<ConfirmTripResponse>

}