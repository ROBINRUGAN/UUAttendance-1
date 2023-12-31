package com.uu.attendance.ui.me.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivityAttendanceAppealBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.ui.me.adapter.AttendanceAppealAdapter
import com.uu.attendance.util.LogUtil.Companion.debug

class AttendanceAppealActivity : BaseToolbarActivity<ActivityAttendanceAppealBinding>() {
    override fun getToolbarTitle(): String {
        return "考勤申诉"
    }

    override fun getViewBelowToolbar(): View {
        return binding.swipe
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvList.adapter = AttendanceAppealAdapter()

        binding.swipe.setOnRefreshListener { doGet() }
        doGet()
    }

    private fun doGet() {
        launch(tryBlock = {
            binding.swipe.isRefreshing = true
            val data = StudentApi.getAttendanceAppealList()
            (binding.rvList.adapter as AttendanceAppealAdapter).setData(data.data!!)
        }, catchBlock = {
            debug(it)
            Toaster.show("获取考勤申诉列表失败")
        }, finallyBlock = {
            binding.swipe.isRefreshing = false
        })
    }
}