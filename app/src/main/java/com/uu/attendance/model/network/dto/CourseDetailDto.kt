package com.uu.attendance.model.network.dto

data class CourseDetailDto(
    val id: Int,
    val name: String,
    val weekday: Int,
    val month: Int,
    val day: Int,
    val sectionStart: Int,
    val sectionEnd: Int,
    val beginTime: String,
    val endTime: String,
    val teacher: Int,
    val place: String,
    val teacherName: String,
    val status: Int
)