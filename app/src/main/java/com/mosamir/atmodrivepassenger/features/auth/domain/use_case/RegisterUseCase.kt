package com.mosamir.atmodrivepassenger.features.auth.domain.use_case

import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
):IRegisterUseCase {
    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String,email:String): IResult<RegisterResponse> {
        return iAuthRepo.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType,email)
    }
}