package com.uu.attendance.model.network.dto

data class ChangePwdDto(
    val no: String,
    val oldPassword: String,
    val newPassword: String
)