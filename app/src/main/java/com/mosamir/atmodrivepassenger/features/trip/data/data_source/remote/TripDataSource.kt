package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.asDomain
import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import java.lang.Exception
import javax.inject.Inject

class TripDataSource @Inject constructor(
    private val tripApiService: TripApiService
):ITripDataSource {


    override suspend fun makeTrip(
        distanceText: String,
        distanceValue: Long,
        durationText: String,
        durationValue: Long
    ): IResult<MakeTripResponse> {
        return try {
            val makeTripData = tripApiService.makeTrip(distanceText,distanceValue,durationText,durationValue)
            if (makeTripData.status){
                return IResult.onSuccess(makeTripData.asDomain())
            }else{
                return IResult.onFail(makeTripData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
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
        return try {
            val confirmTripData = tripApiService.confirmTrip(vehicleClassId,
                pickupLat,
                pickupLng,
                dropOffLat,
                dropOffLng,
                estimateCost,
                estimateTime,
                estimateDistance,
                pickupLocationName,
                dropOffLocationName)
            if (confirmTripData.status){
                return IResult.onSuccess(confirmTripData.asDomain())
            }else{
                return IResult.onFail(confirmTripData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun cancelBeforeCaptain(tripId: Int): IResult<CancelTripResponse> {
        return try {
            val cancelTripData = tripApiService.cancelBeforeCaptain(tripId)
            if (cancelTripData.status){
                return IResult.onSuccess(cancelTripData.asDomain())
            }else{
                return IResult.onFail(cancelTripData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
    }


}