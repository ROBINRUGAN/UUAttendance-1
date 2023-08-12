package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import com.uu.attendance.databinding.FragmentSigninBinding


class SigninFragment : BaseFragment<FragmentSigninBinding>() {

    companion object {
        val instance: SigninFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SigninFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}