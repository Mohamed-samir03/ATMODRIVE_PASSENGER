package com.mosamir.atmodrivepassenger.futures.auth.domain.use_case

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class SendCodeUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
):ISendCodeUseCase {
    override suspend fun sendCode(mobile: String): IResult<LoginResponse> {
        return iAuthRepo.sendCode(mobile)
    }
}