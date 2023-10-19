package com.mosamir.atmodrivepassenger.features.trip.data.data_source.remote


import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteMakeTripResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val MAKE_TRIP = "make-trip"
const val CONFIRM_TRIP = "confirm-trip"

interface TripApiService {

    @POST(MAKE_TRIP)
    @FormUrlEncoded
    suspend fun makeTrip(
        @Field("distance_text") distanceText: String,
        @Field("distance_value") distanceValue: Long,
        @Field("duration_text") durationText: String,
        @Field("duration_value") durationValue: Long
    ):RemoteMakeTripResponse

    @POST(CONFIRM_TRIP)
    @FormUrlEncoded
    suspend fun confirmTrip(
        @Field("vehicle_class_id") vehicleClassId: String,
        @Field("pickup_lat") pickupLat: String,
        @Field("pickup_lng") pickupLng: String,
        @Field("dropoff_lat") dropOffLat: String,
        @Field("dropoff_lng") dropOffLng: String,
        @Field("estimate_cost") estimateCost: String,
        @Field("estimate_time") estimateTime: String,
        @Field("estimate_distance") estimateDistance: String,
        @Field("pickup_location_name") pickupLocationName: String,
        @Field("dropoff_location_name") dropOffLocationName: String,
    ): RemoteConfirmTripResponse

}