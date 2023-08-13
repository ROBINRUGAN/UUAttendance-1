package com.uu.attendance

import android.app.Application
import com.hjq.toast.Toaster
import com.hjq.toast.style.WhiteToastStyle

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Toaster.init(this)
        Toaster.setStyle(WhiteToastStyle())
    }
}