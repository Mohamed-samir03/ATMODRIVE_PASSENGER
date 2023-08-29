package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper


import com.mosamir.atmodrivepassenger.futures.auth.data.model.RemoteCheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse

fun RemoteCheckCodeResponse.asDomain() = CheckCodeResponse(
    data?.asDomain(),
    message,
    status,
    is_new
)