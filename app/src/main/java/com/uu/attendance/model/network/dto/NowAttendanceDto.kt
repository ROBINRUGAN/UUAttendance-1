package com.uu.attendance.model.network.dto

data class NowAttendanceDto(
    val courseId: Int,
    val courseName: String,
    val latitude: String,
    val longitude: String,
    val status: Int
)