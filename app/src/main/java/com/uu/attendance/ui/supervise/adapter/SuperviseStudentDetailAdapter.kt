package com.uu.attendance.ui.supervise.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.CourseStatus
import com.uu.attendance.model.network.dto.SuperviseStudentDto
import com.uu.attendance.ui.supervise.viewmodel.SuperviseViewModel

class SuperviseStudentDetailAdapter(val viewModel: SuperviseViewModel) :
    BaseRecyclerViewAdapter<SuperviseStudentDto>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId = view.findViewById<TextView>(R.id.tv_id)
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
    }

    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_supervise_person
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolder) return
        holder.tvId.text = list[position].studentNo
        holder.tvName.text = list[position].studentName
        holder.tvStatus.text = when (list[position].status) {
            CourseStatus.UNCHECKED -> "未到"
            CourseStatus.CHECKED -> "已到"
            CourseStatus.ABSENT -> "缺课"
            else -> "请假"
        }
        fun getColor(id: Int) = holder.itemView.context.getColor(id)
        holder.tvStatus.setTextColor(
            when (list[position].status) {
                CourseStatus.UNCHECKED -> getColor(R.color.pink)
                CourseStatus.CHECKED -> getColor(R.color.green)
                CourseStatus.ABSENT -> getColor(R.color.red)
                else -> getColor(R.color.blue)
            }
        )
        holder.itemView.setOnClickListener {
            viewModel.currentStudent.value = list[position]
            viewModel.currentFragment.value = 1
        }
    }

    private var originList = mutableListOf<SuperviseStudentDto>()

    @SuppressLint("NotifyDataSetChanged")
    fun filter(s: String) {
        if (s.isEmpty()) {
            if (originList.isNotEmpty()) {
                list.clear()
                list.addAll(originList)
                originList.clear()
            }
        } else {
            if (originList.isEmpty()) originList = list
            list = originList.filter {
                it.studentName.contains(s) || it.studentNo.contains(s)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}