package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.asDomain
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


}