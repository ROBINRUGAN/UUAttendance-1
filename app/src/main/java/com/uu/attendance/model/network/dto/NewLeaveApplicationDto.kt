package com.uu.attendance.model.network.dto

data class NewLeaveApplicationDto(
    val courseId: String,
    val leavePlace: String,
    val appealBeginTime: String,
    val appealEndTime: String,
    val reason: String,
    val status: String
)