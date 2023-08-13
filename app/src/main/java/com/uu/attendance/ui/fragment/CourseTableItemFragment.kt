package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.databinding.FragmentCoursetableItemBinding

class CourseTableItemFragment : BaseFragment<FragmentCoursetableItemBinding>() {

    private lateinit var viewModel: CourseTableViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CourseTableViewModel::class.java)
        binding.viewModel = viewModel
//        Toaster.show(viewModel.currentWeek.value.toString())
    }

}
