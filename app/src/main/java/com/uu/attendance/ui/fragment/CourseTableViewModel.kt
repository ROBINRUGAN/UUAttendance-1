package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uu.attendance.model.bean.CourseBean

class CourseTableViewModel : ViewModel() {
    val currentWeek = MutableLiveData(1)
    val currentMonth = MutableLiveData(1)   // 赋初值1，避免展示出现null使体验不佳
    val courseList = MutableLiveData<Map<Int, List<CourseBean>>>()

    val itemHeight = MutableLiveData<Int>()
    val itemWidth = MutableLiveData<Int>()
}