package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

abstract class BaseToolbarActivity<VB : ViewBinding> : BaseActivity<VB>() {
    abstract fun getToolbarTitle(): String
    private lateinit var toolbar: Toolbar
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