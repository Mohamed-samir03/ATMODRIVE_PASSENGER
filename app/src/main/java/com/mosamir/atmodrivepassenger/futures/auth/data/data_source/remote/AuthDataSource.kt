package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.remote

import com.mosamir.atmodrivepassenger.futures.auth.data.data_source.mapper.asDomain
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.SendCodeResponse
import com.mosamir.atmodrivepassenger.util.IResult
import java.lang.Exception
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authApiService: AuthApiService
):IAuthDataSource {

    override suspend fun sendCode(mobile: String): IResult<SendCodeResponse> {
        return try {
            val sendCodeData = authApiService.sendCode(mobile)
            if (sendCodeData.status == 1){
                return IResult.onSuccess(sendCodeData.asDomain())
            }else{
                return IResult.onFail(sendCodeData.message)
            }
        }catch (e: Exception){
            IResult.onFail(e.localizedMessage)
        }
    }

    override suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<CheckCodeResponse> {
        return try {
            val checkCodeData = authApiService.checkCode(mobile,verificationCode, deviceToken)
            if (checkCodeData.status == 1){
                return IResult.onSuccess(checkCodeData.asDomain())
            }else{
                return IResult.onFail(checkCodeData.message)
            }
        }catch (e: Exception){
            IResult.onFail(e.localizedMessage)
        }
    }

    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String
    ): IResult<CheckCodeResponse> {
        return try {
            val registerData = authApiService.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType)
            if (registerData.status == 1){
                return IResult.onSuccess(registerData.asDomain())
            }else{
                return IResult.onFail(registerData.message)
            }
        }catch (e: Exception){
            IResult.onFail(e.localizedMessage)
        }
    }

}