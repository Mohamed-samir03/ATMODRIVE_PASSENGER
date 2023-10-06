package com.mosamir.atmodrivepassenger.features.auth.domain.model.login

data class DataLogin(
    val is_new: Boolean,
    val user: UserLogin?,
    val full_name:String?
)