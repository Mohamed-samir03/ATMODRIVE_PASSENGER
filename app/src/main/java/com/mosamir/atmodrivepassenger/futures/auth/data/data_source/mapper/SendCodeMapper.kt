package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper

import com.mosamir.atmodrivepassenger.futures.auth.data.model.RemoteSendCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.SendCodeResponse

fun RemoteSendCodeResponse.asDomain() = SendCodeResponse(
    message,
    status
)