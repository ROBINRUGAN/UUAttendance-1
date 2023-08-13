package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.Toolbar
import androidx.core.view.updateMargins
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

abstract class BaseToolbarActivity<VB : ViewBinding> : BaseActivity<VB>() {
    abstract fun getToolbarTitle(): String
    private lateinit var toolbar: Toolbar

    /**
     * 获取toolbar下面的view，以调整距离避免被toolbar遮挡，可空
     */
    open fun getViewBelowToolbar(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = Toolbar(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        (binding.root as ViewGroup).addView(toolbar, 0)
        toolbar.bringToFront()
        setSupportActionBar(toolbar)
        supportActionBar!!.apply {
            title = getToolbarTitle()
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        ImmersionBar.setTitleBar(this, toolbar)

        toolbar.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (getViewBelowToolbar() != null) {
                    val lp = getViewBelowToolbar()!!.layoutParams as ViewGroup.MarginLayoutParams
                    lp.updateMargins(
                        top = lp.topMargin + toolbar.height + ImmersionBar.getStatusBarHeight(
                            this@BaseToolbarActivity
                        )
                    )
                    getViewBelowToolbar()!!.layoutParams = lp
                }
                toolbar.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}