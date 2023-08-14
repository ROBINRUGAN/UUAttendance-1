package com.uu.attendance.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import com.uu.attendance.MyApplication

class ConvertUtil {
    companion object {

        val outMetrics by lazy {
            val displayMetrics = DisplayMetrics()
            val windowManager = MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            displayMetrics
        }

        // dp to px
        val Number.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), outMetrics).toInt()

        // sp tp px
        val Number.sp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), outMetrics)

    }
}