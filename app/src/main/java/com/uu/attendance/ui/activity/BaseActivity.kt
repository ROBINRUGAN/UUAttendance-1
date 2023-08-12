package com.uu.attendance.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.gyf.immersionbar.ImmersionBar

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

}