package com.uu.attendance.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.uu.attendance.R
import com.uu.attendance.model.network.dto.CourseTableDto
import razerdp.basepopup.BasePopupWindow

@SuppressLint("SetTextI18n")
class CourseDetailPopup(context: Context, course: CourseTableDto, currentWeek: Int) :
    BasePopupWindow(context) {

    init {
        setContentView(R.layout.popup_course_detail)
        findViewById<TextView>(R.id.tv_name).text = course.courseDetail.name
        findViewById<TextView>(R.id.tv_teacher).text = course.teacherName
        findViewById<TextView>(R.id.tv_location).text = course.courseDetail.place
        findViewById<TextView>(R.id.tv_time).text =
            course.courseDetail.sectionStart.toString() + "-" + course.courseDetail.sectionEnd.toString() + "节"
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