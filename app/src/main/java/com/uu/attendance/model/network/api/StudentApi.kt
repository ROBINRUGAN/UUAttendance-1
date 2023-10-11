package com.uu.attendance.model.network.api

import com.uu.attendance.model.network.RetrofitProvider
import com.uu.attendance.model.network.dto.SignInDto
import com.uu.attendance.model.network.service.StudentService
import okhttp3.RequestBody

object StudentApi {
    private val studentService by lazy {
        RetrofitProvider.getRetrofit(true).create(StudentService::class.java)
    }

    suspend fun getCourseTable(week: Int, semester: Int) = studentService.getCourseTable(week, semester)

    suspend fun getLeaveApplicationList() = studentService.getLeaveApplicationList()

    suspend fun getLeaveApplicationDetail(leaveId: Int) = studentService.getLeaveApplicationDetail(leaveId)

    suspend fun getAttendanceAppealList() = studentService.getAttendanceAppealList()

    suspend fun getAttendanceAppealDetail(appealId: Int) = studentService.getAttendanceAppealDetail(appealId)

    suspend fun getSemesterAndSchoolOpenTime() = studentService.getSemesterAndSchoolOpenTime()

    suspend fun postLeaveApplication(body: RequestBody) = studentService.postLeaveApplication(body)

    suspend fun postAttendanceAppeal(body: RequestBody) = studentService.postAttendanceAppeal(body)

    suspend fun signIn(body: SignInDto) = studentService.signIn(body)
}