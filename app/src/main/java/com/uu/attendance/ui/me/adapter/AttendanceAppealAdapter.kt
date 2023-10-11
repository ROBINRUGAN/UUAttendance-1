package com.uu.attendance.ui.me.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto
import com.uu.attendance.ui.coursetable.activity.NewAttendanceAppealActivity

class AttendanceAppealAdapter : BaseRecyclerViewAdapter<AttendanceAppealInfoDto>() {
    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_attendance_appeal
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = "申诉课程：" + list[position].courseName
        holder.tvCourseTime.text =
            "课程时间：" + list[position].attendanceAppeal.appealBeginTime //这里应该是申请操作的时间吧?
        holder.tvApplicationReason.text = "申诉原因：" + list[position].attendanceAppeal.reason
        holder.tvStatus.apply {
            when (list[position].attendanceAppeal.status) {
                "0" -> {
                    text = "审核中"
                    setTextColor(context.getColor(R.color.pink))
                }

                "1" -> {
                    text = "已通过"
                    setTextColor(context.getColor(R.color.green))
                }

                "2" -> {
                    text = "未通过"
                    setTextColor(context.getColor(R.color.red))
                }
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, NewAttendanceAppealActivity::class.java).apply {
                putExtra("appealId", list[position].attendanceAppeal.id)
            }
            it.context.startActivity(intent)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_course_name)
        val tvCourseTime = view.findViewById<TextView>(R.id.tv_course_time)
        val tvApplicationReason = view.findViewById<TextView>(R.id.tv_application_reason)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

}