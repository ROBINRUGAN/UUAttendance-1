package com.uu.attendance.model

class Identity {
    companion object {
        const val STUDENT = 1
        const val SUPERVISOR = 2
    }
}

class CourseStatus {
    companion object {
        const val UNCHECKED = 0
        const val CHECKED = 1
        const val ABSENT = 2
        const val LEAVE = 3
    }
}