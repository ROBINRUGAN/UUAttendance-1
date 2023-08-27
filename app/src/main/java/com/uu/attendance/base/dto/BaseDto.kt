package com.uu.attendance.base.dto

data class BaseDto<T>(
    val code: Int,
    val msg: String,
    val data: T?
)