package com.uu.attendance.ui.me.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.dto.LeaveApplicationInfoDto
import com.uu.attendance.ui.coursetable.activity.NewLeaveApplicationActivity

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvName.text = "请假课程：" + list[position].courseName
        holder.tvTime.text = "请假时间：" +
                list[position].leaveApplication.appealBeginTime + " 至 " + list[position].leaveApplication.appealEndTime
        holder.tvStatus.apply {
            when (list[position].leaveApplication.status) {
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
            val intent = Intent(it.context, NewLeaveApplicationActivity::class.java).apply {
                putExtra("view", true)
                putExtra("data", Gson().toJson(list[position]))
            }
            it.context.startActivity(intent)
        }
    }

}