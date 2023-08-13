package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uu.attendance.model.bean.CourseBean

class CourseTableViewModel: ViewModel() {
    val currentWeek = MutableLiveData<Int>()
    val currentMonth = MutableLiveData<Int>()
    val courseList = MutableLiveData<List<CourseBean>>()
}