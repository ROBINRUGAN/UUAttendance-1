package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSuperviseListBinding
import com.uu.attendance.model.network.api.SuperviseApi
import com.uu.attendance.ui.activity.SuperviseViewModel
import com.uu.attendance.ui.adapter.SuperviseStudentDetailAdapter
import com.uu.attendance.util.KeyboardUtil


class SuperviseListFragment : BaseFragment<FragmentSuperviseListBinding>() {

    lateinit var viewModel: SuperviseViewModel
    lateinit var adapter: SuperviseStudentDetailAdapter
    companion object {
        val instance: SuperviseListFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SuperviseListFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SuperviseViewModel::class.java)

        viewModel.isSearch.observe(viewLifecycleOwner) {
            if (it) {
                KeyboardUtil.delayShowSoftInput(requireActivity(), binding.etSearch, 300)
                viewModel.isSearch.value = false
            }
        }

        adapter = SuperviseStudentDetailAdapter(viewModel)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(context)
        launch(tryBlock = {
            val list = SuperviseApi.getCourseAttendanceList(viewModel.courseId).data!!
            adapter.setData(list)
        }, catchBlock = {
            it.printStackTrace()
            Toaster.show("获取课程考勤情况失败")
        })
    }

}