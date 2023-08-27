package com.uu.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityMainBinding
import com.uu.attendance.ui.adapter.ViewPagerMainAdapter
import com.uu.attendance.util.KVUtil

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (KVUtil.get("token", "") == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val navView = binding.navView
        val mainViewPager = binding.mainViewPager

        mainViewPager.isUserInputEnabled = false
        mainViewPager.adapter = ViewPagerMainAdapter(this)
        mainViewPager.offscreenPageLimit = 4

        navView.itemIconTintList = null
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_signin -> {
                    mainViewPager.setCurrentItem(0, false)
                }

                R.id.navigation_coursetable -> {
                    mainViewPager.setCurrentItem(1, false)
                }

                R.id.navigation_supervise -> {
                    mainViewPager.setCurrentItem(2, false)
                }

                R.id.navigation_me -> {
                    mainViewPager.setCurrentItem(3, false)
                }
            }
            true
        }
    }
}