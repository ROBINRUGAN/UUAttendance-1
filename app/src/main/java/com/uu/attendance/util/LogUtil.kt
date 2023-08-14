package com.uu.attendance.util

import android.util.Log

class LogUtil {
    companion object {
        private val TAG = "UUAttendance"
        fun Any.logd() {
            Log.d(TAG, this.toString())
        }
    }
}