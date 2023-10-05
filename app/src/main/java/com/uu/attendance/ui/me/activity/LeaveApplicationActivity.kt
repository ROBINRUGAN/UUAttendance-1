package com.uu.attendance.ui.me.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivityLeaveApplicationBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.ui.me.adapter.ApplicationListAdapter
import com.uu.attendance.util.LogUtil.Companion.debug

class LeaveApplicationActivity : BaseToolbarActivity<ActivityLeaveApplicationBinding>() {
    override fun getToolbarTitle(): String {
        return "请假申请"
    }

    override fun getViewBelowToolbar(): View {
        return binding.swipe
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvApplicationList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvApplicationList.adapter = ApplicationListAdapter()

        binding.swipe.setOnRefreshListener { doGet() }
        doGet()
    }

    private fun doGet() {
        launch(tryBlock = {
            binding.swipe.isRefreshing = true
            val data = StudentApi.getLeaveApplicationList()
            (binding.rvApplicationList.adapter as ApplicationListAdapter).setData(data.data!!)
        }, catchBlock = {
            debug(it)
            Toaster.show("获取请假申请列表失败")
        }, finallyBlock = {
            binding.swipe.isRefreshing = false
        })
    }
}