package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.register

import com.mosamir.atmodrivepassenger.features.auth.data.model.register.RemoteRegisterResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse

fun RemoteRegisterResponse.asDomain() = RegisterResponse(
    data.asDomain(),
    message,
    status,
)