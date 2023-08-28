package com.uu.attendance.ui.fragment

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.updateMargins
import androidx.lifecycle.ViewModelProvider
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentCoursetableItemBinding
import com.uu.attendance.model.bean.CourseBean
import com.uu.attendance.ui.view.CourseDetailPopup
import com.uu.attendance.util.ConvertUtil.Companion.dp
import java.text.SimpleDateFormat
import java.util.Locale

class CourseTableItemFragment : BaseFragment<FragmentCoursetableItemBinding>() {

    private lateinit var viewModel: CourseTableViewModel
    private var fragmentWeek: Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CourseTableViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.currentWeek.observe(viewLifecycleOwner) {
            if (fragmentWeek == viewModel.currentWeek.value) {
//                initHeader(viewModel.courseList.value?.get(fragmentWeek))
//                initBody(viewModel.courseList.value?.get(fragmentWeek))
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initHeader(courseBeans: List<CourseBean>?) {
        if (courseBeans.isNullOrEmpty()) return
        // 无课就不绘制顶部了
        binding.llHeader.removeAllViews()
        val weekdays = listOf("一", "二", "三", "四", "五", "六", "日")
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        var date = formatter.parse(courseBeans.first().date)!!
        var calendar = java.util.Calendar.getInstance().apply { time = date }
        var weekday = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1
        if (weekday == 0) weekday = 7
        date = java.util.Date(date.time + (1 - weekday) * 24 * 60 * 60 * 1000)
        calendar = java.util.Calendar.getInstance().apply { time = date }

        // 左上角月份
        viewModel.currentMonth.value = calendar.get(java.util.Calendar.MONTH) + 1

        val headerList: MutableList<TextView> = mutableListOf()
        for (i in 1..7) {
            val currentDate = java.util.Date(date.time + (i - 1) * 24 * 60 * 60 * 1000)
            val tv = TextView(context).apply {
                text = weekdays[i - 1] + '\n' + currentDate.date.toString()
                textSize = 18f
                setTextColor(resources.getColor(android.R.color.black))
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    viewModel.itemWidth.value!!, LayoutParams.MATCH_PARENT
                )
                (layoutParams as ViewGroup.MarginLayoutParams).updateMargins(left = 5)
            }
            headerList.add(tv)
        }
        headerList.forEach {
            binding.llHeader.addView(it)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initBody(courseBeans: List<CourseBean>?) {
        if (courseBeans.isNullOrEmpty()) return
        binding.flBody.removeAllViews()
        courseBeans.forEach { course ->
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val calendar = java.util.Calendar.getInstance().apply {
                time = formatter.parse(course.date)!!
            }
            var weekday = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1
            if (weekday == 0) weekday = 7
            val llClass = FrameLayout(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(
                    viewModel.itemWidth.value!!,
                    viewModel.itemHeight.value!! * (course.timeEnd - course.timeBegin + 1)
                )
                (layoutParams as ViewGroup.MarginLayoutParams).updateMargins(
                    left = (viewModel.itemWidth.value!! + 5) * (weekday - 1),
                    top = (viewModel.itemHeight.value!! + 5) * (course.timeBegin - 1)
                )
                background = AppCompatResources.getDrawable(context, R.drawable.bg_corner_10)
                tag = course
                val tv = TextView(context).apply {
                    text = course.name + '\n' + course.location
                    textSize = 12f
                    setTextColor(resources.getColor(android.R.color.black))
                    gravity = Gravity.CENTER
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
                val tvStatus = TextView(context).apply {
                    text = when (course.status) {
                        0 -> "已"
                        1 -> "正"
                        2 -> "缺"
                        else -> "请"
                    }
                    textSize = 13f
                    gravity = Gravity.CENTER
                    background = AppCompatResources.getDrawable(context, R.drawable.bg_corner_10)
                    setTextColor(
                        resources.getColor(
                            when (course.status) {
                                0 -> R.color.blue
                                1 -> R.color.green
                                2 -> R.color.pink
                                else -> R.color.black
                            }
                        )
                    )
                    layoutParams = FrameLayout.LayoutParams(
                        20.dp,
                        20.dp
                    ).apply {
                        gravity = Gravity.END or Gravity.TOP
                    }
                }
                setOnClickListener { v ->
                    val course = v.tag as CourseBean
                    CourseDetailPopup(requireContext(), course, viewModel.currentWeek.value!!).showPopupWindow()
                }
                addView(tv)
                addView(tvStatus)
            }
            binding.flBody.addView(llClass)
        }

    }

    companion object {
        fun instance(week: Int): CourseTableItemFragment {
            return CourseTableItemFragment().apply { fragmentWeek = week }
        }
    }

}
