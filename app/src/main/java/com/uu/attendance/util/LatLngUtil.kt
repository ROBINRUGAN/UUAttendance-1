package com.uu.attendance.util

import com.amap.api.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class LatLngUtil {
    companion object {
        // 计算两个经纬度之间的距离，单位为米
        fun getDistance(latLng1: LatLng, latLng2: LatLng): Double {
            val R = 6371000 // 地球半径
            val lat1 = latLng1.latitude
            val lon1 = latLng1.longitude
            val lat2 = latLng2.latitude
            val lon2 = latLng2.longitude
            val radLat1 = Math.toRadians(lat1)
            val radLat2 = Math.toRadians(lat2)
            val deltaLat = radLat1 - radLat2
            val deltaLon = Math.toRadians(lon1) - Math.toRadians(lon2)
            val a = sin(deltaLat / 2) * sin(deltaLat / 2) +
                    cos(radLat1) * cos(radLat2) *
                    sin(deltaLon / 2) * sin(deltaLon / 2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            return R * c
        }

    }
}