package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjq.toast.Toaster
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.CourseTableDto
import com.uu.attendance.util.LogUtil.Companion.debug

class CourseTableViewModel : ViewModel() {
    val currentYear = MutableLiveData(2022)
    val currentWeek = MutableLiveData(1)
    val currentSemester = MutableLiveData(1)
    val currentMonth = MutableLiveData(1)   // 赋初值1，避免展示出现null使体验不佳

    //    val courseList = MutableLiveData<Map<Int, List<CourseBean>>>()
    val courseList = MutableLiveData<List<CourseTableDto>>()

    val itemHeight = MutableLiveData<Int>()
    val itemWidth = MutableLiveData<Int>()


    init {
//        currentWeek.observeForever {
//            coroutineScope {
//
//            }
//            getCourseTable()
//        }
    }

    suspend fun getCourseTable() {
        try {
            courseList.value = StudentApi.getCourseTable(
                currentWeek.value!!,
                currentYear.value!!,
                currentSemester.value!!
            ).data
        } catch (e: Exception) {
            debug(e)
            Toaster.show("获取课表失败")
        }

    }
}