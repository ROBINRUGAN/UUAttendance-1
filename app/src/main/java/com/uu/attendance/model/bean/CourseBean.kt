package com.uu.attendance.model.bean

data class CourseBean(
    val id: Int,
    val name: String,
    val teacher: String,
    val location: String,
    val status: Int,
    val timeBegin: Int,
    val timeEnd: Int,
    val date: String
)
