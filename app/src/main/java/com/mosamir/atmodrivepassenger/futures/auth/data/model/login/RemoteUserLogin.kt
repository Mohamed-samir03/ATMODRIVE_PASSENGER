package com.mosamir.atmodrivepassenger.futures.auth.data.model.login

data class RemoteUserLogin(
    val avatar: String,
    val email: String,
    val full_name: String,
    val is_dark_mode: Int,
    val lang: String,
    val mobile: String,
    val options: RemoteOptionsLogin,
    val passenger_code: String,
    val rate: Int,
    val remember_token: String,
    val shake_phone: Int,
    val status: Int,
    val `suspend`: Int
)