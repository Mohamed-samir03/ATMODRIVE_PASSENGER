package com.mosamir.atmodrivepassenger.futures.auth.data.repository

import com.mosamir.atmodrivepassenger.futures.auth.data.data_source.remote.IAuthDataSource
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.SendCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val iAuthDataSource: IAuthDataSource
) :IAuthRepo{


    override suspend fun sendCode(mobile: String): IResult<SendCodeResponse> {
        return iAuthDataSource.sendCode(mobile)
    }

    override suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<CheckCodeResponse> {
        return  iAuthDataSource.checkCode(mobile,verificationCode, deviceToken)
    }

    override suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String): IResult<CheckCodeResponse> {
        return iAuthDataSource.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType)
    }


}