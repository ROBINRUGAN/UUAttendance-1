package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjq.toast.Toaster
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.CourseDetailDto
import com.uu.attendance.util.LogUtil.Companion.debug
import java.util.Date

class CourseTableViewModel : ViewModel() {
    val currentWeek = MutableLiveData<Int>()
    val currentSemester = MutableLiveData<Int>()
    val currentMonth = MutableLiveData<Int>()
    val schoolOpenTime = MutableLiveData<Date>() // 本学期开学时间

    val courseList = mutableMapOf<Int, List<CourseDetailDto>?>()

    val itemHeight = MutableLiveData<Int>()
    val itemWidth = MutableLiveData<Int>()

    suspend fun getCourseTable(week:Int) {
        try {
            courseList[week] = (
                    StudentApi.getCourseTable(
                        week,
                        currentSemester.value!!
                    ).data)
        } catch (e: Exception) {
            debug(e)
            Toaster.show("获取课表失败")
        }

    }
}