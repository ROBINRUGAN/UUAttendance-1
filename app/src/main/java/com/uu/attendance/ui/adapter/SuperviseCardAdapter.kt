package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.bean.StudentBean

class SuperviseCardAdapter(list: MutableList<StudentBean>) :
    BaseRecyclerViewAdapter<StudentBean>() {
    init {
        this.list = list
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
        holder.tvInfo.text = list[position].studentId.toString() + '\n' + list[position].name
        // todo set avatar
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getOneMoreData() {
        val sb = StudentBean(7, "吴九", 2019210007, "", 0)
        list.add(sb)
        notifyDataSetChanged()  // 暂时使用notifyDataSetChanged，其他方法会导致卡片alpha突变
        // todo 网络获取
    }

}
