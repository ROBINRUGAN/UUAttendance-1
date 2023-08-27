package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSuperviseBinding


class SuperviseFragment : BaseFragment<FragmentSuperviseBinding>() {

    companion object {
        val instance: SuperviseFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SuperviseFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}