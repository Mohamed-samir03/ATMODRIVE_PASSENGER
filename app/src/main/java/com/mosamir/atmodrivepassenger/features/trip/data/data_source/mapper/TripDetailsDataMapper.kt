package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteTripDetailsData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.TripDetailsData

fun RemoteTripDetailsData.asDomain() = TripDetailsData(
    dropoff_lat,
    dropoff_lng,
    dropoff_location_name,
    pickup_lat,
    pickup_lng,
    pickup_location_name,
    start_date,
    trip_id,
    trip_status
)