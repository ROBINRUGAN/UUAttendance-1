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
import com.uu.attendance.model.CourseStatus
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.ui.view.CourseDetailPopup
import com.uu.attendance.util.ConvertUtil.Companion.dp

class CourseTableItemFragment(private val week: Int) :
    BaseFragment<FragmentCoursetableItemBinding>() {

    private lateinit var viewModel: CourseTableViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CourseTableViewModel::class.java)
        binding.viewModel = viewModel

        binding.swipe.setOnRefreshListener { doGet() }
        doGet()

    }

    private fun doGet() {
        launch(tryBlock = {
            viewModel.getCourseTable(week)
        }, catchBlock = {
            it.printStackTrace()
        }, finallyBlock = {
            initHeader()
            initBody(viewModel.courseList[week])
            binding.swipe.isRefreshing = false
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initHeader() {
        binding.llHeader.removeAllViews()
        val weekdays = listOf("一", "二", "三", "四", "五", "六", "日")

        val headerList: MutableList<TextView> = mutableListOf()
        for (i in 1..7) {
            val distance = (week - 1) * 7 + i - 1
            val date =
                java.util.Date(viewModel.schoolOpenTime.value!!.time + distance * 24 * 60 * 60 * 1000L)

            val tv = TextView(context).apply {
                text =
                    weekdays[i - 1] + '\n' + date.date.toString()
                textSize = 18f
                setTextColor(context.getColor(android.R.color.black))
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
    private fun initBody(courseBeans: List<CourseDetailDto>?) {
        if (courseBeans.isNullOrEmpty()) return
        binding.flBody.removeAllViews()
        courseBeans.forEach { course ->

            val timeBegin = course.sectionStart
            val timeEnd = course.sectionEnd
            val llClass = FrameLayout(requireContext()).apply {
                layoutParams = FrameLayout.LayoutParams(
                    viewModel.itemWidth.value!!,
                    viewModel.itemHeight.value!! * (timeEnd - timeBegin + 1)
                )
                (layoutParams as ViewGroup.MarginLayoutParams).updateMargins(
                    left = (viewModel.itemWidth.value!! + 5) * (course.weekday - 1),
                    top = (viewModel.itemHeight.value!! + 5) * (timeBegin - 1)
                )
                background = AppCompatResources.getDrawable(context, R.drawable.bg_corner_10)
                tag = course
                val tv = TextView(context).apply {
                    text = course.name + '\n' + course.place
                    textSize = 12f
                    setTextColor(context.getColor(android.R.color.black))
                    gravity = Gravity.CENTER
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
                val tvStatus = TextView(context).apply {
                    text = when (course.status) {
                        CourseStatus.UNCHECKED -> "未"
                        CourseStatus.CHECKED -> "已"
                        CourseStatus.ABSENT -> "缺"
                        else -> "请"
                    }
                    textSize = 13f
                    gravity = Gravity.CENTER
                    background = AppCompatResources.getDrawable(context, R.drawable.bg_corner_10)
                    setTextColor(
                        context.getColor(
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
                    CourseDetailPopup(
                        requireContext(),
                        v.tag as CourseDetailDto,
                        viewModel.currentWeek.value!!
                    ).showPopupWindow()
                }
                addView(tv)
                addView(tvStatus)
            }
            binding.flBody.addView(llClass)
        }

    }

    companion object {
        fun instance(week: Int): CourseTableItemFragment {
            return CourseTableItemFragment(week)
        }
    }

}
