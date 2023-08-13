package com.uu.attendance.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.ScrollView


/**
 * 解决地图在主scrollview中滑动冲突的问题由于MapView被定义成final class，所以只能在容器中操作了
 * Created by 张玉水 on 2016/7/18.
 */
class MapContainerLayout : RelativeLayout {
    private var scrollView: ScrollView? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun setScrollView(scrollView: ScrollView?) {
        this.scrollView = scrollView
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            scrollView!!.requestDisallowInterceptTouchEvent(false)
        } else {
            scrollView!!.requestDisallowInterceptTouchEvent(true)
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }
}
