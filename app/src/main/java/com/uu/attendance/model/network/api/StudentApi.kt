package com.uu.attendance.model.network.api

import com.uu.attendance.model.network.RetrofitProvider
import com.uu.attendance.model.network.service.StudentService

object StudentApi {
    private val studentService by lazy {
        RetrofitProvider.getRetrofit(true).create(StudentService::class.java)
    }

    suspend fun getCourseTable(week: Int, semester: Int) = studentService.getCourseTable(week, semester)

    suspend fun getLeaveApplicationList() = studentService.getLeaveApplicationList()

    suspend fun getAttendanceAppealList() = studentService.getAttendanceAppealList()

    suspend fun getSemesterAndSchoolOpenTime() = studentService.getSemesterAndSchoolOpenTime()
}