package com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote

import com.mosamir.atmodrivepassenger.features.auth.data.model.login.RemoteLoginResponse
import com.mosamir.atmodrivepassenger.features.auth.data.model.register.RemoteRegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val SEND_CODE = "send-code"
const val CHECK_CODE = "check-code"
const val REGISTER = "register-passenger"

interface AuthApiService {

    @POST(SEND_CODE)
    @FormUrlEncoded
    suspend fun sendCode(@Field("mobile") mobile: String): RemoteLoginResponse

    @POST(CHECK_CODE)
    @FormUrlEncoded
    suspend fun checkCode(@Field("mobile") mobile:String,
                          @Field("verification_code") verificationCode:String,
                          @Field("device_token") deviceToken:String
      ): RemoteLoginResponse

    @POST(REGISTER)
    @FormUrlEncoded
    suspend fun registerUser(@Field("full_name") fullName:String,
                             @Field("mobile") mobile:String,
                             @Field("avatar") avatar:String,
                             @Field("device_token") deviceToken:String,
                             @Field("device_id") deviceId:String,
                             @Field("device_type") deviceType:String,
                             @Field("email") email:String
        ): RemoteRegisterResponse

}