package com.uu.attendance.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uu.attendance.ui.fragment.CourseTableFragment
import com.uu.attendance.ui.fragment.MeFragment
import com.uu.attendance.ui.fragment.SigninFragment
import com.uu.attendance.ui.fragment.SuperviseFragment

class ViewPagerMainAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(
    fragmentActivity
) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SigninFragment()
            1 -> CourseTableFragment()
            2 -> SuperviseFragment()
            else -> MeFragment()
        }
    }

}