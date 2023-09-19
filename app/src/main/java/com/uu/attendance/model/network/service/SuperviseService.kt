package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.CourseStatusDto
import com.uu.attendance.model.network.dto.SuperviseStudentDto
import com.uu.attendance.model.network.dto.SuperviseTasksDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface SuperviseService {
    @GET("/supervisiontasks")
    suspend fun getSuperviseTaskList(): BaseDto<SuperviseTasksDto>

    @GET("/courseAttendances")
    suspend fun getCourseAttendanceList(
        @Query("courseId") courseId: Int
    ): BaseDto<List<SuperviseStudentDto>>

    @GET("/courseAttendances/whoNoCheck")
    suspend fun getWhoNoCheck(
        @Query("courseId") courseId: Int
    ): BaseDto<SuperviseStudentDto>

    @PUT("/courseAttendances/status")
    suspend fun updateCourseAttendanceStatus(
        @Body body: CourseStatusDto
    ): BaseDto<Any>
}