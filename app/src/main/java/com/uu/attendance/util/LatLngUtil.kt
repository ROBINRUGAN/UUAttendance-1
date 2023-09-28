package com.uu.attendance.util

import com.amap.api.maps.model.LatLng
import java.util.regex.Pattern
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class LatLngUtil {
    companion object {
        // 计算两个经纬度之间的距离，单位为米
        fun getDistance(latLng1: LatLng, latLng2: LatLng?): Double {
            if (latLng2 == null) return -1.0
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

        @Deprecated("")
        fun convertToDecimal(degreeString: String): Double {
            val pattern = Pattern.compile("(\\d+)度(\\d+)分(\\d+)秒")
            val matcher = pattern.matcher(degreeString)
            if (matcher.find()) {
                val degrees = matcher.group(1)!!.toDouble()
                val minutes = matcher.group(2)!!.toDouble() / 60
                val seconds = matcher.group(3)!!.toDouble() / 3600
                return degrees + minutes + seconds
            } else {
                throw IllegalArgumentException("Invalid format")
            }
        }


    }
}