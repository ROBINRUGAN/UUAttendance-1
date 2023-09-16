package com.uu.attendance.model.network.dto

data class SuperviseTasksDto(
    val total: String,
    val rows: List<SuperviseTask>
)