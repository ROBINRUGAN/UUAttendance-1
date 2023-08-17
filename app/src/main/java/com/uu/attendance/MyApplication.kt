package com.uu.attendance

import android.app.Application
import com.hjq.toast.ToastStrategy
import com.hjq.toast.Toaster
import com.hjq.toast.style.WhiteToastStyle

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Toaster.init(this)
        Toaster.setStyle(WhiteToastStyle())
        Toaster.setStrategy(ToastStrategy(ToastStrategy.SHOW_STRATEGY_TYPE_QUEUE))
    }
}