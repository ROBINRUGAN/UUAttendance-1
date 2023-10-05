package com.uu.attendance.ui.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amap.api.maps.model.LatLng
import com.uu.attendance.model.network.dto.NowAttendanceDto

class SigninViewModel : ViewModel() {
    val destLatLng: MutableLiveData<LatLng?> = MutableLiveData()
    val nowAttendanceDto: MutableLiveData<NowAttendanceDto?> = MutableLiveData()

    val currentLatLng: MutableLiveData<LatLng> = MutableLiveData()
}