package com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.login

import com.mosamir.atmodrivepassenger.features.auth.data.model.login.RemoteDataLogin
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.DataLogin

fun RemoteDataLogin.asDomain() = DataLogin(
    is_new,
    user?.asDomain() ,
    full_name
)