package com.uu.attendance.model.network.dto

data class LeaveApplicationDetailDto(
    val studentNo: String,
    val studentName: String,
    val courseName: String,
    val leavePlace: String,
    val beginTime: String,
    val endTime: String,
    val reason: String,
    val image: String
)