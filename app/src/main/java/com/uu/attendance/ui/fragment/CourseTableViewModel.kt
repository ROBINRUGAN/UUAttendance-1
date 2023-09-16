package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjq.toast.Toaster
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.util.LogUtil.Companion.debug
import java.util.Date

class CourseTableViewModel : ViewModel() {
    val currentWeek = MutableLiveData(1)
    val currentSemester = MutableLiveData(202301)
    val currentMonth = MutableLiveData(1)   // 赋初值1，避免展示出现null使体验不佳
    val schoolOpenTime = MutableLiveData<Date>() // 本学期开学时间

    //    val courseList = MutableLiveData<Map<Int, List<CourseBean>>>()
    val courseList = mutableMapOf<Int, List<CourseDetailDto>?>()

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

    suspend fun getCourseTable(week:Int) {
        try {
            courseList[week] = (
                    StudentApi.getCourseTable(
                        currentWeek.value!!,
                        currentSemester.value!!
                    ).data)
//            debug(courseList.toString())
        } catch (e: Exception) {
            debug(e)
            Toaster.show("获取课表失败")
        }

    }
}