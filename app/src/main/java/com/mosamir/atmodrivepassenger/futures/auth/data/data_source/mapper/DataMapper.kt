package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper


import com.mosamir.atmodrivepassenger.futures.auth.data.model.RemoteData
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.Data

fun RemoteData.asDomain() = Data(
    avatar,
    email,
    full_name,
    is_dark_mode,
    lang,
    mobile,
    options.asDomain(),
    passenger_code,
    rate,
    remember_token,
    shake_phone,
    status,
    suspend
)