package com.mosamir.atmodrivepassenger.features.trip.data.data_source.mapper

import com.mosamir.atmodrivepassenger.features.trip.data.model.RemoteMakeTripData
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripData

fun RemoteMakeTripData.asDomain() = MakeTripData(
    estimate_cost, estimate_distance, estimate_time, vehicle_class_icon, vehicle_class_name
)