package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.register

import com.mosamir.atmodrivepassenger.features.auth.data.model.register.RemoteOptionsRegister
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.OptionsRegister

fun RemoteOptionsRegister.asDomain() = OptionsRegister(
    device_types,
    gender
)