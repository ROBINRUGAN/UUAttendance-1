package com.uu.attendance.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.uu.attendance.R
import com.uu.attendance.databinding.ActivityMainBinding
import com.uu.attendance.ui.adapter.PagerAdapter
import com.uu.attendance.ui.fragment.CourseTableFragment
import com.uu.attendance.ui.fragment.MeFragment
import com.uu.attendance.ui.fragment.SigninFragment
import com.uu.attendance.ui.fragment.SuperviseFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView = binding.navView
        val mainViewPager = binding.mainViewPager

        mainViewPager.isUserInputEnabled = false

        val fragmentArr = ArrayList<Fragment>()
        fragmentArr.add(SigninFragment.instance)
        fragmentArr.add(CourseTableFragment.instance)
        fragmentArr.add(SuperviseFragment.instance)
        fragmentArr.add(MeFragment.instance)
        mainViewPager.adapter = PagerAdapter(this, fragmentArr)

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