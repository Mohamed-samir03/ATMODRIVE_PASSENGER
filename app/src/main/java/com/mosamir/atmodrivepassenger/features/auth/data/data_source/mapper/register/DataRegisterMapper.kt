package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.register

import com.mosamir.atmodrivepassenger.features.auth.data.model.register.RemoteDataRegister
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.DataRegister

fun RemoteDataRegister.asDomain() = DataRegister(
    id,
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