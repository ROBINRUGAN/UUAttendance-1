package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSuperviseCardBinding
import com.uu.attendance.model.CourseStatus
import com.uu.attendance.model.network.api.SuperviseApi
import com.uu.attendance.model.network.dto.CourseStatusDto
import com.uu.attendance.model.network.dto.SuperviseStudentDto
import com.uu.attendance.ui.adapter.SuperviseCardAdapter
import com.uu.attendance.ui.view.cardswipelayout.CardConfig
import com.uu.attendance.ui.view.cardswipelayout.CardItemTouchHelperCallback
import com.uu.attendance.ui.view.cardswipelayout.CardLayoutManager
import com.uu.attendance.ui.view.cardswipelayout.OnSwipeListener
import com.uu.attendance.ui.viewmodel.SuperviseViewModel


class SuperviseCardFragment : BaseFragment<FragmentSuperviseCardBinding>() {

    lateinit var viewModel: SuperviseViewModel
    lateinit var adapter: SuperviseCardAdapter

    companion object {
        val instance: SuperviseCardFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SuperviseCardFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SuperviseViewModel::class.java)

        binding.etSearch.setOnClickListener {
            viewModel.isSearch.value = true
        }

        viewModel.currentStudent.observe(viewLifecycleOwner) {
            val list = mutableListOf(
                it
            )
            val recyclerView = binding.rvSuperviseList

            adapter = SuperviseCardAdapter(viewModel.courseId, list)
            launch(tryBlock = {
                adapter.getNewData(3)
            })
            val cardCallback = CardItemTouchHelperCallback(adapter, list)
            val touchHelper = ItemTouchHelper(cardCallback)
            val cardLayoutManager = CardLayoutManager(recyclerView, touchHelper)
            recyclerView.layoutManager = cardLayoutManager
            touchHelper.attachToRecyclerView(recyclerView)
            cardCallback.setOnSwipedListener(object : OnSwipeListener<SuperviseStudentDto> {
                override fun onSwiping(
                    viewHolder: RecyclerView.ViewHolder,
                    ratio: Float,
                    direction: Int
                ) {
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    t: SuperviseStudentDto,
                    direction: Int
                ) {
                    launch(tryBlock = {
                        val status = when (direction) {
                            CardConfig.SWIPED_LEFT -> CourseStatus.ABSENT
                            else -> CourseStatus.CHECKED
                        }
                        val dto = CourseStatusDto(
                            viewModel.courseId,
                            status,
                            t.studentId
                        )
                        SuperviseApi.updateCourseAttendanceStatus(dto)
                        Toaster.show(
                            t.studentName + ": " + when (direction) {
                                CardConfig.SWIPED_LEFT -> "未到"
                                else -> "签到"
                            }
                        )
                    }, catchBlock = { e ->
                        e.printStackTrace()
                        Toaster.show("更新学生状态失败")
                    })
                    launch(tryBlock = {
                        adapter.getNewData(1)
                    }, catchBlock = { e ->
                        e.printStackTrace()
                        Toaster.show("获取学生信息失败")
                    })
                }

                override fun onSwipedClear() {
                    Toaster.show("所有任务已完成")
                }
            })
            recyclerView.adapter = adapter
        }


    }

}