package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ICheckCodeUseCase {

    suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<CheckCodeResponse>

}