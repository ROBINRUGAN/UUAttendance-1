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
import com.uu.attendance.model.bean.CourseBean
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
//        viewModel.currentMonth.value = 6
//        viewModel.currentWeek.value = 4

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
        viewModel.itemWidth.value = binding.vpTable.width / 7 - 5

        viewModel.courseList.value = mapOf(
            Pair(
                4, listOf(
                    CourseBean(
                        1,
                        "高等数学",
                        "awa",
                        "教学楼",
                        2,
                        1,
                        2,
                        "2023-08-14",
                    ),
                    CourseBean(
                        2,
                        "高等s学",
                        "aawa",
                        "教学楼",
                        3,
                        2,
                        5,
                        "2023-08-16",
                    ),
                    CourseBean(
                        3,
                        "高b数学",
                        "awwa",
                        "教学楼",
                        0,
                        6,
                        7,
                        "2023-08-18",
                    ),
                    CourseBean(
                        4,
                        "高等数学",
                        "awa",
                        "教学楼",
                        1,
                        7,
                        10,
                        "2023-08-20",
                    ),
                )
            )
        )
        binding.vpTable.adapter = CourseTableAdapter(this)
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