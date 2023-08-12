package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import com.uu.attendance.databinding.FragmentCoursetableBinding


class CourseTableFragment : BaseFragment<FragmentCoursetableBinding>() {

    companion object {
        val instance: CourseTableFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CourseTableFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}