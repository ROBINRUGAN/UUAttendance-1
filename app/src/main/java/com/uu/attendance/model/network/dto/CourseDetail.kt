package com.uu.attendance.model.network.dto

data class CourseDetail(
    val id: Int,
    val name: String,
    val weekday: Int,
    val month:Int,
    val day:Int,
    val sectionStart: Int,
    val sectionEnd:Int,
    val teacher: Int,
    val place: String,
)