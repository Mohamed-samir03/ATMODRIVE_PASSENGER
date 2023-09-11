package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.data.model.login.RemoteLoginResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ICheckCodeUseCase {

    suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<LoginResponse>

}