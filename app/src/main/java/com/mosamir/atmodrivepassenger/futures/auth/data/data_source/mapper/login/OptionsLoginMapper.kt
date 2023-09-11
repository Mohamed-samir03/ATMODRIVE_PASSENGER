package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper.login

import com.mosamir.atmodrivepassenger.futures.auth.data.model.login.RemoteOptionsLogin
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.login.OptionsLogin

fun RemoteOptionsLogin.asDomain() = OptionsLogin(
    device_types,
    gender
)