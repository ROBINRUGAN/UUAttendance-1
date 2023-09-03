package com.uu.attendance.model.network.dto

data class AttendanceAppeal(
    val id: Int,
    val studentId: Int,
    val courseId: Int,
    val appealBeginTime: String,
    val appealEndTime: String,
    val status: String,
    val reason: String
)