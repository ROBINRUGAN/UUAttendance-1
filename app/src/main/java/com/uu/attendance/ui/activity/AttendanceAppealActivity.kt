package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivityAttendanceAppealBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.ui.adapter.AttendanceAppealAdapter
import com.uu.attendance.util.LogUtil.Companion.debug

class AttendanceAppealActivity : BaseToolbarActivity<ActivityAttendanceAppealBinding>() {
    override fun getToolbarTitle(): String {
        return "考勤申诉"
    }

    override fun getViewBelowToolbar(): View {
        return binding.rvList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvList.adapter = AttendanceAppealAdapter()

        launch(tryBlock = {
            val data = StudentApi.getAttendanceAppealList()
            (binding.rvList.adapter as AttendanceAppealAdapter).setData(data.data!!)
        }, catchBlock = {
            debug(it)
            Toaster.show("获取考勤申诉列表失败")
        })
    }
}