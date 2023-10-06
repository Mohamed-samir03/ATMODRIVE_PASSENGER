package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.login

import com.mosamir.atmodrivepassenger.features.auth.data.model.login.RemoteLoginResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse

fun RemoteLoginResponse.asDomain() = LoginResponse(
    data?.asDomain(),
    message,
    status
)