package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper

import com.mosamir.atmodrivepassenger.futures.auth.data.model.RemoteOptions
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.Options

fun RemoteOptions.asDomain() = Options(
    device_types,
    gender
)