package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteMakeTripResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val MAKE_TRIP = "make-trip"

interface TripApiService {

    @POST(MAKE_TRIP)
    @FormUrlEncoded
    suspend fun makeTrip(
        @Field("distance_text") distanceText: String,
        @Field("distance_value") distanceValue: Long,
        @Field("duration_text") durationText: String,
        @Field("duration_value") durationValue: Long
    ):RemoteMakeTripResponse

}