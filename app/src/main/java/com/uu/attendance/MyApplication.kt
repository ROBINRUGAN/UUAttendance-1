package com.uu.attendance

import android.app.Application
import com.hjq.toast.ToastStrategy
import com.hjq.toast.Toaster
import com.hjq.toast.style.WhiteToastStyle
import com.tencent.mmkv.MMKV
import com.uu.attendance.util.setupDefaultCrashHandler

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        setupDefaultCrashHandler()

        Toaster.init(this)
        Toaster.setStyle(WhiteToastStyle())
        Toaster.setStrategy(ToastStrategy(ToastStrategy.SHOW_STRATEGY_TYPE_QUEUE))

        MMKV.initialize(this)
    }
}