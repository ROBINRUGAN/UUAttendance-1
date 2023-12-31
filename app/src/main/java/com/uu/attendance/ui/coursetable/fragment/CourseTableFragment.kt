package com.uu.attendance.ui.coursetable.fragment

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentCoursetableBinding
import com.uu.attendance.ui.coursetable.adapter.CourseTableAdapter
import com.uu.attendance.ui.main.viewmodel.MainViewModel
import com.uu.attendance.util.isOnline


class CourseTableFragment : BaseFragment<FragmentCoursetableBinding>() {

    private lateinit var viewModel: MainViewModel

    companion object {
        val instance: CourseTableFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CourseTableFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.viewModel = viewModel

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
        viewModel.itemHeight.value = binding.llSection.height / 10 - 5
        for (i in 0 until 10) {
            val tv = TextView(context).apply {
                text = (i + 1).toString()
                textSize = 12f
                setTextColor(resources.getColor(android.R.color.black))
                gravity = Gravity.TOP
                layoutParams =
                    LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        viewModel.itemHeight.value!! + 5
                    )
            }
            binding.llSection.addView(tv)
        }
    }

    private fun initViewPager() {
        if (!isOnline(requireContext())) {
            Toaster.show("请连接网络后重启APP")
            return
        }
        viewModel.itemWidth.value = binding.vpTable.width / 7 - 5

        viewModel.currentWeek.observe(this@CourseTableFragment) {
            val distance = (it - 1) * 7
            val date =
                java.util.Date(viewModel.schoolOpenTime.value!!.time + distance * 24 * 60 * 60 * 1000L)
            viewModel.currentMonth.value = date.month + 1
        }

        binding.vpTable.adapter = CourseTableAdapter(this@CourseTableFragment)
        binding.vpTable.setCurrentItem(viewModel.currentWeek.value!! - 1, false)
        binding.vpTable.offscreenPageLimit = 1
        binding.vpTable.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentWeek.value = position + 1
            }
        })
    }
}