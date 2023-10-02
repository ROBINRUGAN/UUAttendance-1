package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.ChangePwdDto
import com.uu.attendance.model.network.dto.LoginDto
import com.uu.attendance.model.network.dto.UserDto
import com.uu.attendance.model.network.dto.UserInfoDto
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountService {

    @POST("user/login")
    suspend fun login(@Body body: LoginDto): BaseDto<UserDto>

    @POST("user/logout")
    suspend fun logout(): Response

    @GET("students/studentInfo")
    suspend fun getInfo(): BaseDto<UserInfoDto>

    @GET("user/authenticate")
    suspend fun authenticate(): BaseDto<Int>

    @PUT("user/password")
    suspend fun changePwd(@Body body: ChangePwdDto): BaseDto<Any>
}