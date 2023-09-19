package com.uu.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityMainBinding
import com.uu.attendance.model.Identity
import com.uu.attendance.model.network.api.AccountApi
import com.uu.attendance.ui.adapter.ViewPagerMainAdapter
import com.uu.attendance.util.KVUtil
import com.uu.attendance.util.LogUtil.Companion.debug

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

        switchSuperviseVisibility()
        launch(tryBlock = {
            val identity = AccountApi.authenticate()
            KVUtil.put("identity", identity.data!!)
            switchSuperviseVisibility()
        }, catchBlock = {
            debug(it)
            Toaster.show("获取用户信息异常，请重新登录")
            KVUtil.put("identity", Identity.STUDENT)
            switchSuperviseVisibility()
        })
    }

    private fun switchSuperviseVisibility() {
        binding.navView.menu.findItem(R.id.navigation_supervise).isVisible =
            KVUtil.get("identity", Identity.STUDENT) == Identity.SUPERVISOR
    }
}