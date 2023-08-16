package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.databinding.FragmentSuperviseListBinding
import com.uu.attendance.ui.activity.SuperviseViewModel
import com.uu.attendance.util.KeyboardUtil


class SuperviseListFragment : BaseFragment<FragmentSuperviseListBinding>() {

    lateinit var viewModel: SuperviseViewModel
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
    }

}