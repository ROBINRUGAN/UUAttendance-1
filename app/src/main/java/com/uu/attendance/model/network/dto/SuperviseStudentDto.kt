package com.uu.attendance.model.network.dto

data class SuperviseStudentDto(
    val id: Int,
    val studentId: Int,
    val studentNo: String,
    val studentName: String,
    val courseId: Int,
    val status: Int
)