package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.View
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivityRulesBinding

class RulesActivity : BaseToolbarActivity<ActivityRulesBinding>() {
    override fun getToolbarTitle(): String {
        return "考勤规则"
    }

    override fun getViewBelowToolbar(): View {
        return binding.llRules
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}