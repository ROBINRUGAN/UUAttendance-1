package com.uu.attendance.model.network.api

import com.uu.attendance.model.network.RetrofitProvider
import com.uu.attendance.model.network.dto.ChangePwdDto
import com.uu.attendance.model.network.dto.LoginDto
import com.uu.attendance.model.network.service.AccountService

object AccountApi {
    private val accountService by lazy {
        RetrofitProvider.getRetrofit(true).create(AccountService::class.java)
    }

    suspend fun login(username: String, password: String) =
        accountService.login(LoginDto(username, password))

    suspend fun logout() = accountService.logout()

    suspend fun getInfo() = accountService.getInfo()

    suspend fun authenticate() = accountService.authenticate()

    suspend fun changePwd(body: ChangePwdDto) = accountService.changePwd(body)
}