package com.uu.attendance.ui.supervise.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uu.attendance.model.network.dto.SuperviseStudentDto

class SuperviseViewModel : ViewModel() {
    var courseId: Int = -1

    val isSearch = MutableLiveData(false)

    val currentFragment = MutableLiveData(0)

    val currentStudent = MutableLiveData<SuperviseStudentDto>()
}