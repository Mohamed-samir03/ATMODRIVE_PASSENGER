package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
):IRegisterUseCase {
    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String): IResult<CheckCodeResponse> {
        return iAuthRepo.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType)
    }
}