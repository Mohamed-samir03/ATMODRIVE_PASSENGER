package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.SendCodeResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ISendCodeUseCase {

    suspend fun sendCode(mobile: String): IResult<SendCodeResponse>

}