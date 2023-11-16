package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper.ontrip

import com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip.RemoteEndTripDetails
import com.mosamir.atmodrivepassenger.features.trip.data.model.ontrip.RemoteOnTripData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ontrip.OnTripData

fun RemoteOnTripData.asDomain() = OnTripData(
    captain_details?.asDomain(),
    dropoff_lat,
    dropoff_lng,
    dropoff_location_name,
    end_trip_details.asDomain(),
    pickup_lat,
    pickup_lng,
    pickup_location_name,
    real_trip,
    start_date,
    trip_id,
    trip_status
)