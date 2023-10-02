package com.uu.attendance.model.network.dto

data class SignInDto(
    val courseId: Int,
    val longitude: Double,
    val latitude: Double
)