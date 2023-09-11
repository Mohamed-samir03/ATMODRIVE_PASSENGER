package com.mosamir.atmodrivepassenger.futures.auth.data.model.register

data class RemoteRegisterResponse(
    val `data`: RemoteDataRegister,
    val message: String,
    val status: Boolean
)