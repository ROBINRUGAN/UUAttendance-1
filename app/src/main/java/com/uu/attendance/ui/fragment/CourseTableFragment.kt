package com.uu.attendance.ui.fragment

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.databinding.FragmentCoursetableBinding
import com.uu.attendance.ui.adapter.CourseTableAdapter


class CourseTableFragment : BaseFragment<FragmentCoursetableBinding>() {

    private lateinit var viewModel: CourseTableViewModel

    companion object {
        val instance: CourseTableFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CourseTableFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CourseTableViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.currentMonth.value = 6
        viewModel.currentWeek.value = 1

        binding.llSection.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                initSection()
                initViewPager()
                binding.llSection.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initSection() {
        val totalHeight = binding.llSection.height
        val itemHeight = totalHeight / 10
        for (i in 0 until 10) {
            val tv = TextView(context).apply {
                text = (i + 1).toString()
                textSize = 12f
                setTextColor(resources.getColor(android.R.color.black))
                gravity = Gravity.TOP
                layoutParams =
                    LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, itemHeight)
            }
            binding.llSection.addView(tv)
        }
    }

    private fun initViewPager() {
        binding.vpTable.adapter = CourseTableAdapter(this)
        binding.vpTable.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.currentWeek.value = position + 1
            }
        })
    }
}