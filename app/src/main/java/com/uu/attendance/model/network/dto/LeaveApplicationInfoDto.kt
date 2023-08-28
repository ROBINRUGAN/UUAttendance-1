package com.uu.attendance.model.network.dto

data class LeaveApplicationInfoDto(
    val leaveApplicationDto: LeaveApplicationDto,
    val courseName: String
)