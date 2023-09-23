package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.dto.SuperviseTask
import com.uu.attendance.ui.activity.SuperviseDetailActivity

class SuperviseListAdapter : BaseRecyclerViewAdapter<SuperviseTask>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvClassName = view.findViewById<TextView>(R.id.tv_class_name)
        val tvClassTime = view.findViewById<TextView>(R.id.tv_class_time)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_supervise_class
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvClassName.text = list[position].courseName
        holder.tvClassTime.text =
            "上课时间：" + list[position].beginTime + "-" + list[position].endTime
        holder.tvStatus.text = if (position == 0) "预点名" else ""
        holder.itemView.setOnClickListener {
            if (position == 0) {
                val intent = Intent(it.context, SuperviseDetailActivity::class.java)
                intent.putExtra("courseId", list[position].courseId)
                it.context.startActivity(intent)
            } else {
                Toaster.show("该课程尚未开始点名")
            }
        }
    }

}