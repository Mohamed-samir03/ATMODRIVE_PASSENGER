package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper.login

import com.mosamir.atmodrivepassenger.futures.auth.data.model.login.RemoteUserLogin
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.login.UserLogin

fun RemoteUserLogin.asDomain() = UserLogin(
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