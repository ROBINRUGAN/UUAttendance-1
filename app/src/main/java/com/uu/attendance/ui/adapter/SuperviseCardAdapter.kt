package com.uu.attendance.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.model.bean.StudentBean

class SuperviseCardAdapter(list: MutableList<StudentBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mList = list

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<StudentBean>) {
        this.mList.clear()
        this.mList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_supervise_card, parent, false)
        return CardViewHolder(view)
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInfo = view.findViewById<TextView>(R.id.tv_student)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is CardViewHolder) return   //impossible
        holder.tvInfo.text = mList[position].studentId.toString() + '\n' + mList[position].name
        // todo set avatar
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getOneMoreData() {
        val sb = StudentBean(7, "吴九", 2019210007, "", 0)
        mList.add(sb)
        notifyDataSetChanged()  // 暂时使用notifyDataSetChanged，其他方法会导致卡片alpha突变
        // todo 网络获取
    }

}
