package com.mosamir.atmodrivepassenger.features.auth.domain.repository

import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IAuthRepo {

    suspend fun sendCode(mobile: String): IResult<LoginResponse>

    suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<LoginResponse>

    suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String, email:String): IResult<RegisterResponse>

}