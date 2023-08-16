package com.uu.attendance.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.Timer
import java.util.TimerTask


class KeyboardUtil {
    companion object {
        /**
         * 延时弹出软键盘
         *
         * @param activity 当前activity
         * @param view     当前获取焦点的view
         * @param delay    延时弹出的时间
         */
        fun delayShowSoftInput(activity: Activity, view: View?, delay: Long) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    activity.runOnUiThread { showSoftInput(view) }
                }
            }, delay)
        }

        /**
         * 弹出软键盘
         * @param view 当前获取焦点的view
         */
        fun showSoftInput(view: View?) {
            if (view != null) {
                view.isFocusable = true
                view.isFocusableInTouchMode = true
                view.requestFocus() //获取焦点
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                //if (!imm.isActive()) //没有显示键盘，弹出
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        /**
         * 隐藏软键盘
         *
         * @param view         当前获取焦点的view
         * @param isClearFocus true释放焦点， false保留焦点
         */
        fun hideSoftKeyboard(view: View?, isClearFocus: Boolean) {
            if (view != null) {
                if (isClearFocus) view.clearFocus()
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) //有显示键盘，隐藏
                    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

}