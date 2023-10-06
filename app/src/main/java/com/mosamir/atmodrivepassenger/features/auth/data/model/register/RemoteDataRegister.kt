package com.mosamir.atmodrivepassenger.features.auth.data.model.register

data class RemoteDataRegister(
    val avatar: String,
    val email: Any?,
    val full_name: String,
    val is_dark_mode: Int,
    val lang: String,
    val mobile: String,
    val options: RemoteOptionsRegister,
    val passenger_code: String,
    val rate: Int,
    val remember_token: String,
    val shake_phone: Int,
    val status: Int,
    val `suspend`: Int
)