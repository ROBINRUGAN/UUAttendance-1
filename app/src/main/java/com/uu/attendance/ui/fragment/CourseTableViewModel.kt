package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uu.attendance.model.network.dto.CourseDto

class CourseTableViewModel : ViewModel() {
    val currentYear = MutableLiveData(2022)
    val currentWeek = MutableLiveData(1)
    val currentSemester = MutableLiveData(1)
    val currentMonth = MutableLiveData(1)   // 赋初值1，避免展示出现null使体验不佳
//    val courseList = MutableLiveData<Map<Int, List<CourseBean>>>()
    val courseList = MutableLiveData<List<CourseDto>>()

    val itemHeight = MutableLiveData<Int>()
    val itemWidth = MutableLiveData<Int>()

//    init {
//        courseList.value = StudentApi.getCourseTable(1,2022,2).data
//
//    }
}