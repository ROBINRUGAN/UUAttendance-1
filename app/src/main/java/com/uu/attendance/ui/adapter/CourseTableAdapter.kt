package com.uu.attendance.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uu.attendance.ui.fragment.CourseTableItemFragment

class CourseTableAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 18 // 总周数
    }

    override fun createFragment(position: Int): Fragment {
        return CourseTableItemFragment.instance(position + 1)
    }

}
