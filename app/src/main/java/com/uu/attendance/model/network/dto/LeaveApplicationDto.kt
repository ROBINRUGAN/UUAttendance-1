package com.uu.attendance.model.network.dto

data class LeaveApplicationDto(
    val id: Int?,
    val studentId: Int?,
    val courseId: Int,
    val reason: String,
    val leavePlace: String,
    val appealBeginTime: String,
    val appealEndTime: String,
    val status: String //申请时固定为字符串"0"
)