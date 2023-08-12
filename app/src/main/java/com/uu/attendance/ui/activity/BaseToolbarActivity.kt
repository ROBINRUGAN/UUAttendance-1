package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseToolbarActivity<VB : ViewBinding> : BaseActivity<VB>() {
    abstract fun getToolbarTitle(): String
    private lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = Toolbar(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.apply {
            title = getToolbarTitle()
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            //setHomeAsUpIndicator(R.drawable.ic_new_back_18dp)
        }

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