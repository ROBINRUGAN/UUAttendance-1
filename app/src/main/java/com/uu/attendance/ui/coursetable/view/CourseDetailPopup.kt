package com.uu.attendance.ui.coursetable.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.model.CourseStatus
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.ui.coursetable.activity.NewAttendanceAppealActivity
import com.uu.attendance.ui.coursetable.activity.NewLeaveApplicationActivity
import razerdp.basepopup.BasePopupWindow

@SuppressLint("SetTextI18n")
class CourseDetailPopup(context: Context, course: CourseDetailDto, currentWeek: Int) :
    BasePopupWindow(context) {

    init {
        setContentView(R.layout.popup_course_detail)
        findViewById<TextView>(R.id.tv_name).text = course.name
        findViewById<TextView>(R.id.tv_teacher).text = course.teacherName
        findViewById<TextView>(R.id.tv_location).text = course.place
        findViewById<TextView>(R.id.tv_time).text =
            course.sectionStart.toString() + "-" + course.sectionEnd.toString() + "节"
        findViewById<TextView>(R.id.tv_week).text = "第${currentWeek}周"
        findViewById<TextView>(R.id.tv_leave_apply).setOnClickListener {
            when (course.status) {
                CourseStatus.UNCHECKED -> {
                    val intent = Intent(context, NewLeaveApplicationActivity::class.java)
                    val bundle = Bundle().apply {
                        putInt("courseId", course.id)
                        putString("courseName", course.name)
                        putString("beginTime", course.beginTime)
                        putString("endTime", course.endTime)
                    }
                    intent.putExtra("bundle", bundle)
                    context.startActivity(intent)
                }

                CourseStatus.LEAVE -> {
                    Toaster.show("您已请假")
                }

                else -> {
                    Toaster.show("只有未签到情况下可以请假")
                }
            }
        }
        findViewById<TextView>(R.id.tv_appeal).setOnClickListener {
            if (course.status == CourseStatus.ABSENT) {
                val intent = Intent(context, NewAttendanceAppealActivity::class.java)
                val bundle = Bundle().apply {
                    putInt("courseId", course.id)
                    putString("courseName", course.name)
                    putString("beginTime", course.beginTime)
                    putString("endTime", course.endTime)
                }
                intent.putExtra("bundle", bundle)
                context.startActivity(intent)
            } else {
                Toaster.show("您未缺勤，无需申诉")
            }
        }
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