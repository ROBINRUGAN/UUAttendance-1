package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.LoginDto
import com.uu.attendance.model.network.dto.UserDto
import com.uu.attendance.model.network.dto.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {

    @POST("user/login")
    suspend fun login(@Body body: LoginDto): BaseDto<UserDto>

    @GET("students/studentInfo")
    suspend fun getInfo(): BaseDto<UserInfoDto>

    @GET("user/authenticate")
    suspend fun authenticate(): BaseDto<Int>
}