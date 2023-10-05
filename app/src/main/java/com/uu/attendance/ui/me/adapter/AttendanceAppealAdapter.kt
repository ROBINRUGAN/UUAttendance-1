package com.uu.attendance.ui.me.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto

class AttendanceAppealAdapter : BaseRecyclerViewAdapter<AttendanceAppealInfoDto>() {
    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_attendance_appeal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = list[position].courseName
        holder.tvCourseTime.text = list[position].attendanceAppeal.appealBeginTime //这里应该是申请操作的时间吧?
        holder.tvApplicationReason.text = list[position].attendanceAppeal.reason
        holder.tvStatus.text = list[position].attendanceAppeal.status
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_course_name)
        val tvCourseTime = view.findViewById<TextView>(R.id.tv_course_time)
        val tvApplicationReason = view.findViewById<TextView>(R.id.tv_application_reason)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

}