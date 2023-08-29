package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.CourseDto
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StudentService {

    @GET("courseAttendances/courseDetailWithStatus")
    suspend fun getCourseTable(
        @Query("week") week: Int,
        @Query("year") year: Int,
        @Query("semester") semester: Int
    ): BaseDto<List<CourseDto>>

    @GET("leaves/student")
    suspend fun getLeaveApplicationList(): BaseDto<List<LeaveApplicationInfoDto>>
}