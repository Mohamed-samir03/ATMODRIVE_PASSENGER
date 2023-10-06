package com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote

import com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.login.asDomain
import com.mosamir.atmodrivepassenger.features.auth.data.data_source.mapper.register.asDomain
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authApiService: AuthApiService
):IAuthDataSource {

    override suspend fun sendCode(mobile: String): IResult<LoginResponse> {
        return try {
            val sendCodeData = authApiService.sendCode(mobile)
            if (sendCodeData.status){
                return IResult.onSuccess(sendCodeData.asDomain())
            }else{
                return IResult.onFail(sendCodeData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<LoginResponse> {
        return try {
            val checkCodeData = authApiService.checkCode(mobile,verificationCode, deviceToken)
            if (checkCodeData.status){
                return IResult.onSuccess(checkCodeData.asDomain())
            }else{
                return IResult.onFail(checkCodeData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String, email:String
    ): IResult<RegisterResponse> {
        return try {
            val registerData = authApiService.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType, email)
            if (registerData.status){
                return IResult.onSuccess(registerData.asDomain())
            }else{
                return IResult.onFail(registerData.message)
            }
        }catch (e: Exception){
            IResult.onFail(NetworkState.getErrorMessage(e).msg.toString())
        }
    }

}