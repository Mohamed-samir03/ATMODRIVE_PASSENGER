package com.mosamir.atmodrivepassenger.features.auth.domain.model.register

data class OptionsRegister(
    val device_types: List<String>,
    val gender: List<String>
)