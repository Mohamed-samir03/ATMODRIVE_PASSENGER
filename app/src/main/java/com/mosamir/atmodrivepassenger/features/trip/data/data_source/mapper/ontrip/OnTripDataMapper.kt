package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.ontrip

import com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip.RemoteOnTripData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.OnTripData

fun RemoteOnTripData.asDomain() = OnTripData(
    dropoff_lat,
    dropoff_lng,
    dropoff_location_name,
    pickup_lat,
    pickup_lng,
    pickup_location_name,
    start_date,
    trip_id,
    trip_status,
    car_brand,
    car_model
)