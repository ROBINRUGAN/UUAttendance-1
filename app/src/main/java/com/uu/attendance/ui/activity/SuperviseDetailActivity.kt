package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivitySuperviseDetailBinding
import com.uu.attendance.ui.adapter.ViewPagerSuperviseDetailAdapter
import com.uu.attendance.ui.viewmodel.SuperviseViewModel

class SuperviseDetailActivity : BaseToolbarActivity<ActivitySuperviseDetailBinding>() {

    lateinit var viewModel: SuperviseViewModel
    override fun getToolbarTitle(): String {
        return "督导详情"
    }

    override fun getViewBelowToolbar(): View {
        return binding.vp2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SuperviseViewModel::class.java)

        viewModel.courseId = intent.getIntExtra("courseId", -1)
        if (viewModel.courseId == -1) {
            finish()
            return
        }


        binding.vp2.adapter = ViewPagerSuperviseDetailAdapter(this)
        binding.vp2.isUserInputEnabled = false
        binding.vp2.offscreenPageLimit = 2

//        binding.vp2.currentItem = 1  // debug

        viewModel.currentFragment.observe(this) {
            binding.vp2.currentItem = it
        }
    }

    override fun onBackPressed() {
        if (viewModel.currentFragment.value == 1) {
            viewModel.currentFragment.value = 0
        } else {
            super.onBackPressed()
        }
    }

}