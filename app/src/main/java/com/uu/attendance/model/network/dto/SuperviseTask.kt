package com.uu.attendance.model.network.dto

data class SuperviseTask(
    val courseId: Int,
    val courseName: String,
    val beginTime: String,
    val endTime: String,
    val semester: Int,
    val weekday: Int
)