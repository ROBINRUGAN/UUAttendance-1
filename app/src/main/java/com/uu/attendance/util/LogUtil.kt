package com.uu.attendance.util

import android.util.Log
import com.uu.attendance.BuildConfig

class LogUtil {
    companion object {
        private val TAG = "UUAttendance"
        fun Any.logd() {
            Log.d(TAG, this.toString())
        }

        fun Any.debug(msg: Any) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, msg.toString())
            }
        }
    }
}