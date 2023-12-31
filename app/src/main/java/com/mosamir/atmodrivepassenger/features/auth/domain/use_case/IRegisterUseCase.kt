package com.mosamir.atmodrivepassenger.features.auth.domain.use_case

import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IRegisterUseCase {

    suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String, email:String): IResult<RegisterResponse>

}