package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.AttendanceAppealDetailDto
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.model.network.dto.LeaveApplicationDetailDto
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto
import com.uu.attendance.model.network.dto.SemesterInfoDao
import com.uu.attendance.model.network.dto.SignInDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentService {

    @GET("courseAttendances/courseTableInfo")
    suspend fun getCourseTable(
        @Query("week") week: Int,
        @Query("semester") semester: Int
    ): BaseDto<List<CourseDetailDto>>

    @GET("leaves/student")
    suspend fun getLeaveApplicationList(): BaseDto<List<LeaveApplicationInfoDto>>

    @GET("leaves/{leaveId}/leaveDetail")
    suspend fun getLeaveApplicationDetail(
        @Path("leaveId") leaveId: Int
    ): BaseDto<LeaveApplicationDetailDto>

    @GET("attendanceAppeals/student")
    suspend fun getAttendanceAppealList(): BaseDto<List<AttendanceAppealInfoDto>>

    @GET("attendanceAppeals/{attendanceAppealId}/attendanceAppealDetail")
    suspend fun getAttendanceAppealDetail(
        @Path("attendanceAppealId") appealId: Int
    ): BaseDto<AttendanceAppealDetailDto>

    @GET("/courseDetails/dataColumn")
    suspend fun getSemesterAndSchoolOpenTime(): BaseDto<SemesterInfoDao>

    @POST("/leaves")
    suspend fun postLeaveApplication(
        @Body body: RequestBody
    ): BaseDto<Any>

    @POST("attendanceAppeals")
    suspend fun postAttendanceAppeal(
        @Body body: RequestBody
    ): BaseDto<Any>

    @PUT("/courseAttendances/signin")
    suspend fun signIn(
        @Body body: SignInDto
    ): BaseDto<Any>

}