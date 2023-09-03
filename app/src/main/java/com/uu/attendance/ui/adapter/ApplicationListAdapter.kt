package com.uu.attendance.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto

class ApplicationListAdapter : BaseRecyclerViewAdapter<LeaveApplicationInfoDto>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_course_name)
        val tvTime = view.findViewById<TextView>(R.id.tv_application_time)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_leave_application
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = list[position].courseName
        holder.tvTime.text = list[position].leaveApplicationDto.appealBeginTime //这里应该是申请操作的时间吧?
        holder.tvStatus.text = list[position].leaveApplicationDto.status
    }

}