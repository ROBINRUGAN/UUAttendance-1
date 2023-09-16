package com.uu.attendance.model.network.service

import com.uu.attendance.base.dto.BaseDto
import com.uu.attendance.model.network.dto.SuperviseTasksDto
import retrofit2.http.GET

interface SuperviseService {
    @GET("/supervisiontasks")
    suspend fun getSuperviseTaskList(): BaseDto<SuperviseTasksDto>
}