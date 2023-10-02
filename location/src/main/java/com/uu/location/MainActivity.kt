package com.uu.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions


class MainActivity : AppCompatActivity() {
    private var latitude = 0.0
    private var longitude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        XXPermissions.with(this)
            .permission(Permission.ACCESS_COARSE_LOCATION)
            .permission(Permission.ACCESS_FINE_LOCATION)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        initLocation()
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    Toast.makeText(this@MainActivity, "请授予定位权限", Toast.LENGTH_SHORT).show()
                    finish()
                }

            })

        findViewById<Button>(R.id.btn_copy_lat).setOnClickListener {
            copy(latitude.toString())
        }
        findViewById<Button>(R.id.btn_copy_lng).setOnClickListener {
            copy(longitude.toString())
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria() // 创建一个定位准则对象
        criteria.accuracy = Criteria.ACCURACY_COARSE // 设置定位精确度
        criteria.isAltitudeRequired = false // 设置是否需要海拔信息
        criteria.isBearingRequired = true // 设置是否需要方位信息
        criteria.isCostAllowed = false // 设置是否允许运营商收费
        criteria.powerRequirement = Criteria.POWER_HIGH // 设置对电源的需求
//        val providers = locationManager.getProviders(criteria, true)
        val bestProvider = locationManager.getBestProvider(criteria, true)
        if (bestProvider != null) {
            locationManager.requestLocationUpdates(
                bestProvider,
                300,// 间隔多少毫秒通知
                0.5f,// 最小间隔距离变化通知
                mLocationListener
            )
        } else {
            Toast.makeText(this, "bestProvider为空，请打开定位服务", Toast.LENGTH_SHORT).show()
        }
    }

    // 定义一个位置变更监听器
    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            findViewById<TextView>(R.id.tv).text = "经度：$longitude\n纬度：$latitude"
        }

        //当禁用的提供程序被调用时会立即调用
        override fun onProviderDisabled(arg0: String) {}

        //用户启用提供程序时被调用
        override fun onProviderEnabled(arg0: String) {}

        //状态变化时被调用
        override fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {}
    }

    private fun copy(s: String) {
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("text", s)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "已复制到剪贴板: $s", Toast.LENGTH_SHORT).show()
    }
}