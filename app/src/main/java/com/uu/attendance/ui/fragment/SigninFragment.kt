package com.uu.attendance.ui.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.amap.api.maps.AMap
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.google.gson.Gson
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSigninBinding
import com.uu.attendance.model.CourseStatus
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.NowAttendanceDto
import com.uu.attendance.model.network.dto.SignInDto
import com.uu.attendance.util.KVUtil
import com.uu.attendance.util.LatLngUtil
import com.uu.attendance.util.LogUtil.Companion.debug
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit


class SigninFragment : BaseFragment<FragmentSigninBinding>() {

    lateinit var viewModel: SigninViewModel
    lateinit var webSocket: WebSocket
    lateinit var client: OkHttpClient


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
                        initWebSocket()
                    }
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    Toaster.show("请在设置中打开定位权限，否则签到功能无法正常使用！")
                }
            })


    }

    @SuppressLint("SetTextI18n")
    private fun initWebSocket() {

        client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor {
                val token: String = KVUtil.get("token", "")
                var request: Request = it.request()
                request = request.newBuilder().header("Authorization", token).build()
                it.proceed(request)
            }
            .build()

        val request = Request.Builder()
            .url("ws://111.229.173.12:9090/websocket/studentNowAttendances/" + KVUtil.get("id", -1))
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                debug("onOpen")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                debug("ReceivedText: $text")
                try {
                    viewModel.nowAttendanceDto.postValue(
                        Gson().fromJson(text, NowAttendanceDto::class.java)
                    )
                } catch (e: Exception) {
                    debug(e)
                    Toaster.show("获取签到信息失败")
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                debug("Closed: $code $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                debug("Error: ${t.message}")
            }
        }

        webSocket = client.newWebSocket(request, listener)


        viewModel.nowAttendanceDto.observe(viewLifecycleOwner) {
            if (it == null) {
                viewModel.destLatLng.value = null
                binding.tvClassName.text = "UU考勤"
                binding.btnSignin.text = "无签到"
                binding.btnSignin.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.bg_btn_signin_nosign
                )
                binding.btnSignin.setOnClickListener { }
                //binding.tvSigninHint.text = "现无签到"
                binding.tvLocationHint.visibility = View.GONE
            } else {
                try {
//                    val lat = LatLngUtil.convertToDecimal(it.latitude)
//                    val lng = LatLngUtil.convertToDecimal(it.longitude)
//                    val latlng = LatLng(lat, lng, false)
                    val latlng = LatLng(it.latitude.toDouble(), it.longitude.toDouble(), false)
                    setDestination(latlng)
                } catch (e: Exception) {
                    debug(e)
                    Toaster.show("处理定位信息失败")
                }
                binding.tvClassName.text = it.courseName
                binding.btnSignin.text = when (it.status) {
                    CourseStatus.UNCHECKED -> "未签到"
                    CourseStatus.CHECKED -> "已签到"
                    else -> throw IllegalStateException("status should be UNCHECKED or CHECKED")
                }
                binding.btnSignin.background = AppCompatResources.getDrawable(
                    requireContext(),
                    when (it.status) {
                        CourseStatus.UNCHECKED -> R.drawable.bg_btn_signin_notsigned
                        CourseStatus.CHECKED -> R.drawable.bg_btn_signin_signed
                        else -> throw IllegalStateException("status should be UNCHECKED or CHECKED")
                    }
                )
                binding.btnSignin.setOnClickListener {
                    if (binding.tvLocationHint.text == "属于签到范围") { //todo 修改判定条件
                        val dlg = ProgressDialog(requireContext())
                        dlg.setMessage("正在签到")
                        dlg.show()
                        launch(tryBlock = {
                            val dto = SignInDto(
                                viewModel.nowAttendanceDto.value!!.courseId,
                                viewModel.currentLatLng.value!!.longitude.toString(),
                                viewModel.currentLatLng.value!!.latitude.toString()
                            )
                            val result = StudentApi.signIn(dto)
                            Toaster.show(result.msg)
                        }, catchBlock = {
                            it.printStackTrace()
                            Toaster.show("签到出错，请重试")
                        }, finallyBlock = {
                            dlg.dismiss()
                        })
                    } else {
                        Toaster.show("请移动到签到范围后进行签到")
                    }
                }
                binding.tvLocationHint.visibility = View.VISIBLE
            }

        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initMap(savedInstanceState: Bundle?) {
        MapsInitializer.updatePrivacyShow(context, true, true)
        MapsInitializer.updatePrivacyAgree(context, true)
        binding.mapView.onCreate(savedInstanceState)
        aMap = binding.mapView.map
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

        viewModel.currentLatLng.observe(viewLifecycleOwner) {
            if (it.latitude == 0.0 || it.longitude == 0.0) return@observe //not prepared
            debug("lat" + it.latitude)
            debug("lng" + it.longitude)
            viewModel.destLatLng.value?.let {
                debug("destlat" + it.latitude)
                debug("destlng" + it.longitude)
            }
            val distance = LatLngUtil.getDistance(it, viewModel.destLatLng.value)
            if (distance == -1.0) {
                binding.tvLocationHint.visibility = View.GONE
            } else {
                binding.tvLocationHint.visibility = View.VISIBLE
                if (distance - 20 > 0) {
                    binding.tvLocationHint.text = "距离签到范围还有 ${"%.1f".format(distance)} 米"
                    binding.tvLocationHint.setTextColor(requireContext().getColor(R.color.pink))
                } else {
                    binding.tvLocationHint.text = "属于签到范围"
                    binding.tvLocationHint.setTextColor(requireContext().getColor(R.color.green))
                }
            }
        }
    }

    private fun setDestination(latLng: LatLng?) {
        viewModel.destLatLng.value = latLng
        if (latLng != null) {
            aMap.addMarker(
                MarkerOptions().position(latLng).title("签到点").snippet("DefaultMarker")
            )
        } else {
            //todo remove marker
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
        // 释放资源
        client.dispatcher.executorService.shutdown()
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