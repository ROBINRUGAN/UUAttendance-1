package com.uu.attendance.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hjq.toast.Toaster
import com.uu.attendance.R
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityMainBinding
import com.uu.attendance.model.Identity
import com.uu.attendance.model.network.api.AccountApi
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.ui.fragment.CourseTableFragment
import com.uu.attendance.ui.fragment.MeFragment
import com.uu.attendance.ui.fragment.SigninFragment
import com.uu.attendance.ui.fragment.SuperviseFragment
import com.uu.attendance.ui.viewmodel.MainViewModel
import com.uu.attendance.util.KVUtil
import com.uu.attendance.util.LogUtil.Companion.debug
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewModel: MainViewModel
    private val fragments = listOf(
        lazy { SigninFragment() },
        lazy { CourseTableFragment() },
        lazy { SuperviseFragment() },
        lazy { MeFragment() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (KVUtil.get("token", "") == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val navView = binding.navView

        navView.itemIconTintList = null
        navView.setOnItemSelectedListener { item ->
            val id = when (item.itemId) {
                R.id.navigation_signin -> 0
                R.id.navigation_coursetable -> 1
                R.id.navigation_supervise -> 2
                else -> 3
            }
            val transaction = supportFragmentManager.beginTransaction()
            fragments.filter { it.isInitialized() }.forEach {
                transaction.hide(it.value)
            }
            val fragment = fragments[id]
            if (!fragment.isInitialized()) {
                transaction.add(R.id.fragment_container, fragment.value)
            }
            transaction.show(fragment.value).commit()

            true
        }
        navView.selectedItemId = R.id.navigation_signin

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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        launch(tryBlock = {
            StudentApi.getSemesterAndSchoolOpenTime().data!!.let {
                viewModel.currentSemester.value = it.semester.toInt()
                val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA)
                viewModel.schoolOpenTime.value = Date(Calendar.getInstance().apply {
                    time = formatter.parse(it.schoolOpenTime)!!
                }.timeInMillis)
                val nowDate = Date(System.currentTimeMillis())
                val distance =
                    (nowDate.time - viewModel.schoolOpenTime.value!!.time) / (24 * 60 * 60 * 1000L)
                viewModel.currentWeek.value = (distance / 7).toInt() + 1
            }
        }, catchBlock = {
            it.printStackTrace()
            Toaster.show("获取学期信息失败")
        })
    }

    private fun switchSuperviseVisibility() {
        binding.navView.menu.findItem(R.id.navigation_supervise).isVisible =
            KVUtil.get("identity", Identity.STUDENT) == Identity.SUPERVISOR
    }
}