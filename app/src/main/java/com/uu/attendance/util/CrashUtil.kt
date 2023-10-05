package com.uu.attendance.util

import android.widget.Toast
import com.uu.attendance.BuildConfig
import com.uu.attendance.MyApplication
import java.io.File

fun setupDefaultCrashHandler() {
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
            Toast.makeText(
                MyApplication.instance,
                "未捕获异常：" + throwable.message,
                Toast.LENGTH_LONG
            ).show()
        }

        val file = File(
            MyApplication.instance.getExternalFilesDir(null),
            "Crash${System.currentTimeMillis()}.txt"
        )
        file.writeText(
            "Version: " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE.toString() + ")\n\n" +
                    throwable.stackTraceToString()
        )
    }
}
