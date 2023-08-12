package com.uu.attendance.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter : FragmentStateAdapter {
    private var fragments: List<Fragment>

    constructor(fragmentActivity: FragmentActivity, fragments: List<Fragment>) : super(
        fragmentActivity
    ) {
        this.fragments = fragments
    }

    constructor(fragment: Fragment, fragments: List<Fragment>) : super(
        fragment
    ) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}