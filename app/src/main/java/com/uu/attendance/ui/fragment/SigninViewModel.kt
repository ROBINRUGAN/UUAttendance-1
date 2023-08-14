package com.uu.attendance.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amap.api.maps.model.LatLng

class SigninViewModel : ViewModel() {
    val destLatLng: MutableLiveData<LatLng> = MutableLiveData()

    val currentLatLng: MutableLiveData<LatLng> = MutableLiveData()
}