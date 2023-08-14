package com.uu.attendance.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.uu.attendance.R
import com.uu.attendance.model.bean.CourseBean
import razerdp.basepopup.BasePopupWindow

@SuppressLint("SetTextI18n")
class CourseDetailPopup(context: Context, course: CourseBean, currentWeek: Int) :
    BasePopupWindow(context) {

    init {
        setContentView(R.layout.popup_course_detail)
        findViewById<TextView>(R.id.tv_name).text = course.name
        findViewById<TextView>(R.id.tv_teacher).text = course.teacher
        findViewById<TextView>(R.id.tv_location).text = course.location
        findViewById<TextView>(R.id.tv_time).text = "${course.timeBegin}-${course.timeEnd}节"
        findViewById<TextView>(R.id.tv_week).text = "第${currentWeek}周"
    }

    override fun onCreateShowAnimation(): Animation {
        //这里完成展示动画
        val showAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1f,
            Animation.RELATIVE_TO_SELF,
            0f
        )
        showAnimation.duration = 250
        return showAnimation
    }

    override fun onCreateDismissAnimation(): Animation {
        //这里完成消失动画
        val dismissAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            1f
        )
        dismissAnimation.duration = 250
        return dismissAnimation
    }

}