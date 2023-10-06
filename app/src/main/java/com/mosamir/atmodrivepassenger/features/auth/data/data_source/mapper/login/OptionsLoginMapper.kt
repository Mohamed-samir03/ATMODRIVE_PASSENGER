package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.login

import com.mosamir.atmodrivepassenger.features.auth.data.model.login.RemoteOptionsLogin
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.OptionsLogin

fun RemoteOptionsLogin.asDomain() = OptionsLogin(
    device_types,
    gender
)