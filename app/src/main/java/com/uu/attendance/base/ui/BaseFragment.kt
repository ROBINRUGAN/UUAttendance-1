package com.uu.attendance.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.uu.attendance.BuildConfig
import com.uu.attendance.util.CoroutineUtil.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFragment<VB : ViewBinding> : Fragment(),
    FragmentBinding<VB> by FragmentBindingDelegate() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return createViewWithBinding(inflater, container)
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