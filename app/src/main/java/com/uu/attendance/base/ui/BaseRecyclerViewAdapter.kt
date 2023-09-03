package com.uu.attendance.base.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var list: MutableList<T> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<T>) {
        list = data.toMutableList()
        notifyDataSetChanged()
    }

    abstract fun getViewHolder(viewType: Int, view: View): RecyclerView.ViewHolder

    abstract fun getViewHolderId(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(getViewHolderId(viewType), parent, false)
        return getViewHolder(viewType, view)
    }

    override fun getItemCount() = list.size

    abstract override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

}