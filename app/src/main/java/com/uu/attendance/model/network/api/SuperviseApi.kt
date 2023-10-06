package com.uu.attendance.model.network.api

import com.uu.attendance.model.network.RetrofitProvider
import com.uu.attendance.model.network.dto.CourseStatusDto
import com.uu.attendance.model.network.service.SuperviseService

object SuperviseApi {
    private val superviseService by lazy {
        RetrofitProvider.getRetrofit(true).create(SuperviseService::class.java)
    }

    suspend fun getSuperviseTaskList() = superviseService.getSuperviseTaskList()

    suspend fun getCourseAttendanceList(courseId: Int) =
        superviseService.getCourseAttendanceList(courseId)

    suspend fun getWhoNoCheck(courseId: Int, number: Int, exist: Array<Int>) =
        superviseService.getWhoNoCheck(courseId, number, exist.joinToString(","))

    suspend fun updateCourseAttendanceStatus(courseStatusDto: CourseStatusDto) =
        superviseService.updateCourseAttendanceStatus(courseStatusDto)
}