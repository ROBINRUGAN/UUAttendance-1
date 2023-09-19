package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.api.SuperviseApi
import com.uu.attendance.model.network.dto.SuperviseStudentDto

class SuperviseCardAdapter(val courseId: Int, first: MutableList<SuperviseStudentDto>) :
    BaseRecyclerViewAdapter<SuperviseStudentDto>() {
    init {
        this.list = first
    }

    override fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder {
        return CardViewHolder(view)
    }

    override fun getViewHolderId(viewType: Int): Int {
        return R.layout.item_supervise_card
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInfo = view.findViewById<TextView>(R.id.tv_student)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is CardViewHolder) return   //impossible
        holder.tvInfo.text = list[position].studentId.toString() + '\n' + list[position].studentName
        // todo set avatar
    }

    @SuppressLint("NotifyDataSetChanged")
    suspend fun getOneMoreData() {
        val sb = SuperviseApi.getWhoNoCheck(courseId).data ?: return
        list.add(sb)
        notifyDataSetChanged()  // 暂时使用notifyDataSetChanged，其他方法会导致卡片alpha突变
        // todo 网络获取
    }

}
