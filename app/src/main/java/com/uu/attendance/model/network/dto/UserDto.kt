package com.uu.attendance.model.network.dto

data class UserDto(
    val id: Int,
    val identity: String,
    val name: String,
    val token: String
)