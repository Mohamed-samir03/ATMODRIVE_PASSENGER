package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.captain.RemoteCaptainData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainData

fun RemoteCaptainData.asDomain() = CaptainData(
    avatar,
    blood_type,
    captain_code,
    dropoff_lat,
    dropoff_lng,
    dropoff_location_name,
    estimate_cost,
    estimate_time,
    full_name,
    gender,
    id,
    mobile,
    pickup_lat,
    pickup_lng,
    pickup_location_name,
    rate,
    trip_color,
    vehicle,
    vehicle_class,
    vehicle_model,
    vehicle_registration_plate
)