package com.uu.attendance.model.network.dto

data class CourseTableDto(
    val courseDetail: CourseDetail,
    val status: Int,
    val teacherName: String
)