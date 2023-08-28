package com.uu.attendance.model.network.dto

data class CourseDto(
    val id: Int,
    val courseName: String,
    val teacherName: String,
    val week: String,
    val weekday: String,
    val section: String,
    val beginTime: String,
    val endTime: String,
    val coursePlace: String,
    val status: String
)