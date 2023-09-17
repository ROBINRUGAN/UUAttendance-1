package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSuperviseBinding
import com.uu.attendance.model.network.api.SuperviseApi
import com.uu.attendance.ui.adapter.SuperviseListAdapter


class SuperviseFragment : BaseFragment<FragmentSuperviseBinding>() {

    lateinit var sadapter: SuperviseListAdapter

    companion object {
        val instance: SuperviseFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SuperviseFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sadapter = SuperviseListAdapter()
        binding.rvSuperviselist.apply {
            adapter = sadapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.swipe.setOnRefreshListener { doGet() }
        doGet()

    }

    private fun doGet() {
        launch(tryBlock = {
            binding.swipe.isRefreshing = true
            SuperviseApi.getSuperviseTaskList().data?.rows?.let { sadapter.setData(it) }
        }, catchBlock = {
            it.printStackTrace()
            Toaster.show("获取督导数据失败")
        }, finallyBlock = {
            binding.swipe.isRefreshing = false
        })
    }
}