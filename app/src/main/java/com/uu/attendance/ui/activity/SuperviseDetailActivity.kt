package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivitySuperviseDetailBinding
import com.uu.attendance.ui.adapter.ViewPagerSuperviseDetailAdapter

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

        binding.vp2.adapter = ViewPagerSuperviseDetailAdapter(this)
        binding.vp2.isUserInputEnabled = false
        binding.vp2.offscreenPageLimit = 2

//        binding.vp2.currentItem = 1  // debug

        viewModel.isSearch.observe(this) {
            if (it) {
                binding.vp2.currentItem = 0
            }
        }
    }

    override fun onBackPressed() {
        if (binding.vp2.currentItem == 1) {
            binding.vp2.currentItem = 0
        } else {
            super.onBackPressed()
        }
    }

}