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
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentSigninBinding
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

    private fun initWebSocket() {

// 构建OkHttpClient配置初始化一些参数
        client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor {
                val token: String = KVUtil.get("token", "")
                var request: Request = it.request()
                request = request.newBuilder().header("Authorization", token).build()
                it.proceed(request)
            }
            .build()

// 使用WebSocket的Url地址连接
        val request = Request.Builder()
            .url("ws://111.229.173.12:9090/websocket/studentNowAttendances/" + KVUtil.get("id", -1))
            .build()

// 设置WebSocket的连接状态回调和消息回调
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // 连接成功后，使用WebSocket发送消息
                webSocket.send("Hello, world!")
                debug("onOpen")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // 接收到服务器的消息
                debug("Received: $text")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // 连接关闭
                debug("Closed: $code $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // 连接失败
                debug("Error: ${t.message}")
            }
        }

// 创建WebSocket实例
        webSocket = client.newWebSocket(request, listener)
        debug("webSocket: $webSocket")


//        val uri: URI? = URI.create(
//            "ws://111.229.173.12:9090/websocket/studentNowAttendances/"
//                    + KVUtil.get("id", -1)
//        )


//        val client = OkHttpClient()
//        val request: Request = Request.Builder().url(
//            "ws://111.229.173.12:9090/websocket/studentNowAttendances/"
//                    + KVUtil.get("id", -1)
//        ).build()
//
//        webSocket = client.newWebSocket(request, object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                super.onOpen(webSocket, response)
//                debug("onOpen")
//                // 连接成功时的处理
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                super.onMessage(webSocket, text)
//                debug("onMessage: $text")
//                // 接收到消息时的处理
//            }
//
//            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//                super.onClosing(webSocket, code, reason)
//                debug("onClosing")
//                // 关闭连接时的处理
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//                super.onFailure(webSocket, t, response)
//                debug("onFailure, t: $t")
//                // 连接失败时的处理
//            }
//        })


//        debug(uri.toString())
//        client = object : MyWebSocketClient(uri) {
//            override fun onMessage(message: String?) {
//                super.onMessage(message)
////                val record = Gson().fromJson(message, MsgRecord::class.java)
////                MsgDatabase.getDatabase().msgRecordDao().insertMsgRecord(record)
////                sendNotification(record)
//            }
//        }
//
//        if (!client.isOpen) {
//            if (client.readyState == ReadyState.NOT_YET_CONNECTED) {
//                try {
//                    client.connectBlocking()
//                } catch (e: IllegalStateException) {
//                    debug(e)
//                }
//            } else if (client.readyState == ReadyState.CLOSING || client.readyState == ReadyState.CLOSED) {
//                client.reconnectBlocking()
//            }
//        }
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