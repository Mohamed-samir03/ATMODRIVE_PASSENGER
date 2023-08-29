package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class CheckCodeUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
):ICheckCodeUseCase {
    override suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<CheckCodeResponse> {
        return iAuthRepo.checkCode(mobile,verificationCode, deviceToken)
    }
}