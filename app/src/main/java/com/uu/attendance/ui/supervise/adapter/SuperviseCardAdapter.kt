package com.uu.attendance.ui.supervise.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseRecyclerViewAdapter
import com.uu.attendance.model.network.api.SuperviseApi
import com.uu.attendance.model.network.dto.SuperviseStudentDto
import com.uu.attendance.util.LogUtil.Companion.debug

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
        holder.tvInfo.text = list[position].studentNo + '\n' + list[position].studentName
        // todo set avatar
    }

    suspend fun getNewData(number: Int) {
        val ex = list.map { it.studentId }.toTypedArray()
        debug(ex.joinToString())
        val sb =
            SuperviseApi.getWhoNoCheck(courseId, number, ex).data
                ?: return
        sb.forEach {
            list.add(it)
            debug(it.studentId)
            notifyItemInserted(list.size - 1) //要一个一个加，才能正确布局
        }
    }

}
