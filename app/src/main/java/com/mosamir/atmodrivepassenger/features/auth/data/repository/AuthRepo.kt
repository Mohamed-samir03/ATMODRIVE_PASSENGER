package com.mosamir.atmodrivepassenger.features.auth.data.repository

import com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote.IAuthDataSource
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val iAuthDataSource: IAuthDataSource
) :IAuthRepo{


    override suspend fun sendCode(mobile: String): IResult<LoginResponse> {
        return iAuthDataSource.sendCode(mobile)
    }

    override suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<LoginResponse> {
        return  iAuthDataSource.checkCode(mobile,verificationCode, deviceToken)
    }

    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String, email:String): IResult<RegisterResponse> {
        return iAuthDataSource.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType, email)
    }


}