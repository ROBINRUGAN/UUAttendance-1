package com.uu.attendance.model.network.api

import com.uu.attendance.model.network.RetrofitProvider
import com.uu.attendance.model.network.service.SuperviseService

object SuperviseApi {
    private val superviseService by lazy {
        RetrofitProvider.getRetrofit(true).create(SuperviseService::class.java)
    }

    suspend fun getSuperviseTaskList() = superviseService.getSuperviseTaskList()
}