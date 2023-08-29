package com.mosamir.atmodrivepassenger.futures.auth.data.data_source.remote

import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.SendCodeResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IAuthDataSource {

    suspend fun sendCode(mobile: String): IResult<SendCodeResponse>

    suspend fun checkCode(mobile:String,verificationCode:String, deviceToken:String): IResult<CheckCodeResponse>

    suspend fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String): IResult<CheckCodeResponse>

}