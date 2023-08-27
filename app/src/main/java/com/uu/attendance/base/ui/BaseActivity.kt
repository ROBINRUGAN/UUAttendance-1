package com.uu.attendance.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.gyf.immersionbar.ImmersionBar
import com.uu.attendance.BuildConfig
import com.uu.attendance.util.CoroutineUtil.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()

        ImmersionBar.with(this)
            .transparentBar()
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()


    }

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit): Job {
        return lifecycleScope.launch(context, start, tryBlock = block, catchBlock = {
            if (BuildConfig.DEBUG) {
                throw it
            }
            //ignore
        })
    }


    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: (suspend CoroutineScope.(Throwable) -> Unit) = {},
        finallyBlock: (suspend CoroutineScope.() -> Unit) = {}
    ) = lifecycleScope.launch(context, start, tryBlock, catchBlock, finallyBlock)


}