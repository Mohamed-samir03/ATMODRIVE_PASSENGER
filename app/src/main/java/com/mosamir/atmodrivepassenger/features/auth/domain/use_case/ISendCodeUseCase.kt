package com.mosamir.atmodrivepassenger.features.auth.domain.use_case

import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ISendCodeUseCase {

    suspend fun sendCode(mobile: String): IResult<LoginResponse>

}