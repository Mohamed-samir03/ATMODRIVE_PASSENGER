package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper.register

import com.mosamir.atmodrivepassenger.futures.auth.data.model.register.RemoteRegisterResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.register.RegisterResponse

fun RemoteRegisterResponse.asDomain() = RegisterResponse(
    data.asDomain(),
    message,
    status,
)