package com.mosamir.atmodrivepassenger.futures.auth.domain.model.register

data class RegisterResponse(
    val `data`: DataRegister,
    val message: String,
    val status: Boolean
)