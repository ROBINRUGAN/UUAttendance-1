package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
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
                viewModel.currentFragment.value = 0
                KeyboardUtil.delayShowSoftInput(requireActivity(), binding.etSearch, 300)
                viewModel.isSearch.value = false
            }
        }

        adapter = SuperviseStudentDetailAdapter(viewModel)
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(context)

        binding.etSearch.addTextChangedListener {
            adapter.filter(it.toString())
        }

//        doGet()
    }

    override fun onResume() {
        super.onResume()
//        Toaster.show("onResume")
        doGet()
    }
    private fun doGet() {
        launch(tryBlock = {
            val list = SuperviseApi.getCourseAttendanceList(viewModel.courseId).data!!
            adapter.setData(list)
            adapter.filter(binding.etSearch.text.toString())
        }, catchBlock = {
            it.printStackTrace()
            Toaster.show("获取课程考勤情况失败")
        })
    }

}