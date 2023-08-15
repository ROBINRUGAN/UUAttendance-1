package com.uu.attendance.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.amap.api.maps.AMap
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.databinding.FragmentSigninBinding
import com.uu.attendance.util.LatLngUtil


class SigninFragment : BaseFragment<FragmentSigninBinding>() {

    lateinit var viewModel: SigninViewModel

    companion object {
        val instance: SigninFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SigninFragment()
        }
    }

    private lateinit var aMap: AMap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)

        initMap(savedInstanceState)

        XXPermissions.with(this)
            .permission(Permission.ACCESS_COARSE_LOCATION)
            .permission(Permission.ACCESS_FINE_LOCATION)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                    if (all) {
                        initLocation()
                    }
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    Toaster.show("请在设置中打开定位权限，否则签到功能无法正常使用！")
                }
            })


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initMap(savedInstanceState: Bundle?) {
        MapsInitializer.updatePrivacyShow(context, true, true)
        MapsInitializer.updatePrivacyAgree(context, true)
        binding.mapView.onCreate(savedInstanceState)
        aMap = binding.mapView.map

        // 处理滑动事件冲突
        binding.mapContainerLayout.setScrollView(binding.scrollView)

    }

    @SuppressLint("SetTextI18n")
    fun initLocation() {
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        aMap.myLocationStyle = myLocationStyle //设置定位蓝点的Style

        aMap.uiSettings.isMyLocationButtonEnabled = true //设置默认定位按钮是否显示，非必需设置。
        aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        aMap.addOnMyLocationChangeListener { location ->
            viewModel.currentLatLng.value = LatLng(location.latitude, location.longitude)
        }

        val latLng = LatLng(30.652, 120.013)
        setDestination(latLng)
        viewModel.currentLatLng.observe(viewLifecycleOwner) {
            val distance = LatLngUtil.getDistance(it, viewModel.destLatLng.value!!) - 20
            if (distance > 0) {
                binding.tvLocationHint.text = "距离签到范围还有 ${"%.1f".format(distance)} 米"
                binding.tvLocationHint.setTextColor(resources.getColor(R.color.pink))
            } else {
                binding.tvLocationHint.text = "属于签到范围"
                binding.tvLocationHint.setTextColor(resources.getColor(R.color.green))
            }
        }
    }

    private fun setDestination(latLng: LatLng) {
        viewModel.destLatLng.value = latLng
        aMap.addMarker(MarkerOptions().position(latLng).title("教室").snippet("DefaultMarker"))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        aMap.isMyLocationEnabled = true
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
        aMap.isMyLocationEnabled = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}