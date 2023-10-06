package com.mosamir.atmodrivepassenger.features.auth.domain.model.register

data class RegisterResponse(
    val `data`: DataRegister,
    val message: String,
    val status: Boolean
)