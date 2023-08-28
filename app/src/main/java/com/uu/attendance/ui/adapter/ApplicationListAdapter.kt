package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto

class ApplicationListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<LeaveApplicationInfoDto> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<LeaveApplicationInfoDto>) {
        list = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_course_name)
        val tvTime = view.findViewById<TextView>(R.id.tv_application_time)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leave_application, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = list[position].courseName
        holder.tvTime.text = list[position].leaveApplicationDto.appealBeginTime //这里应该是申请操作的时间吧?
        holder.tvStatus.text = list[position].leaveApplicationDto.status
    }

}