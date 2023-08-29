package com.mosamir.atmodrivepassenger.futures.auth.data.model

data class RemoteData(
    val avatar: String,
    val email: Any,
    val full_name: String,
    val is_dark_mode: Int,
    val lang: String,
    val mobile: String,
    val options: RemoteOptions,
    val passenger_code: String,
    val rate: Int,
    val remember_token: String,
    val shake_phone: Int,
    val status: Int,
    val `suspend`: Int
)