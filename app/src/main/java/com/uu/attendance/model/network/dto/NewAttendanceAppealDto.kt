package com.uu.attendance.model.network.dto

data class NewAttendanceAppealDto(
    val appealBeginTime: String,
    val appealEndTime: String,
    val courseId: String,
    val reason: String,
    val status: String
)
