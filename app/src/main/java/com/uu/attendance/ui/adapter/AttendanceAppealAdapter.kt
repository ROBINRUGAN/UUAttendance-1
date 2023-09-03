package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto

class AttendanceAppealAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<AttendanceAppealInfoDto> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AttendanceAppealInfoDto>) {
        list = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_course_name)
        val tvCourseTime = view.findViewById<TextView>(R.id.tv_course_time)
        val tvApplicationReason = view.findViewById<TextView>(R.id.tv_application_reason)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendance_appeal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = list[position].courseName
        holder.tvCourseTime.text = list[position].attendanceAppeal.appealBeginTime //这里应该是申请操作的时间吧?
        holder.tvApplicationReason.text = list[position].attendanceAppeal.reason
        holder.tvStatus.text = list[position].attendanceAppeal.status
    }

}