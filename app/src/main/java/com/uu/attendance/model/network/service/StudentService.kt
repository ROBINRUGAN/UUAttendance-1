package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto
import com.uu.attendance.model.network.dto.NewLeaveApplicationDto
import com.uu.attendance.model.network.dto.SemesterInfoDao
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudentService {

    @GET("courseAttendances/courseTableInfo")
    suspend fun getCourseTable(
        @Query("week") week: Int,
        @Query("semester") semester: Int
    ): BaseDto<List<CourseDetailDto>>

    @GET("leaves/student")
    suspend fun getLeaveApplicationList(): BaseDto<List<LeaveApplicationInfoDto>>

    @GET("attendanceAppeals/student")
    suspend fun getAttendanceAppealList(): BaseDto<List<AttendanceAppealInfoDto>>

    @GET("/courseDetails/dataColumn")
    suspend fun getSemesterAndSchoolOpenTime(): BaseDto<SemesterInfoDao>

    @POST("/leaves")
    suspend fun postLeaveApplication(
        @Body body: NewLeaveApplicationDto
    ): BaseDto<Any>

}