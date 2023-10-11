package com.uu.attendance.model.network.dto

data class AttendanceAppealDetailDto(
    val studentNo: String,
    val studentName: String,
    val courseName: String,
    val beginTime: String,
    val endTime: String,
    val reason: String,
    val image: String
)