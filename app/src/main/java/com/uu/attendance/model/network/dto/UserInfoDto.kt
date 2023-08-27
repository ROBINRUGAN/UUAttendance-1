package com.uu.attendance.model.network.dto

data class UserInfoDto(
    val `class`: String,
    val college: String,
    val gender: String,
    val grade: String,
    val id: Int,
    val major: String,
    val name: String,
    val no: String
)