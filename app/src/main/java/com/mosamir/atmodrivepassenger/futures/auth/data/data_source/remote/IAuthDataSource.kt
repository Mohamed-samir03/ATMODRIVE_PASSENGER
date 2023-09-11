package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.remote

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IAuthDataSource {

    suspend fun sendCode(mobile: String): IResult<LoginResponse>

    suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<LoginResponse>

    suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String,email:String): IResult<RegisterResponse>

}