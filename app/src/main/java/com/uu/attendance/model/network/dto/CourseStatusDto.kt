package com.uu.attendance.model.network.dto

data class CourseStatusDto(
    val courseId: Int,
    val status: Int,
    val studentId: Int
)