package com.uu.attendance.ui.coursetable.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityNewAttendanceAppealBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.AttendanceAppealInfoDto
import com.uu.attendance.model.network.dto.NewAttendanceAppealDto

class NewAttendanceAppealActivity : BaseActivity<ActivityNewAttendanceAppealBinding>() {
    private val progress by lazy {
        ProgressDialog(this).apply {
            setMessage("提交中...")
        }
    }

    private var viewMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMode = intent.getBooleanExtra("view", false)
        if (viewMode) {
            binding.etReason.isEnabled = false
            binding.btnSubmit.visibility = android.view.View.GONE
            val data =
                Gson().fromJson(intent.getStringExtra("data"), AttendanceAppealInfoDto::class.java)
            binding.tvCourseName.text = data.courseName
            binding.tvBegintime.text = data.attendanceAppeal.appealBeginTime
            binding.tvEndtime.text = data.attendanceAppeal.appealEndTime
            binding.etReason.setText(data.attendanceAppeal.reason)
        } else {
            intent.getBundleExtra("bundle")?.let {
                binding.tvCourseName.text = it.getString("courseName")
                binding.tvBegintime.text = it.getString("beginTime")
                binding.tvEndtime.text = it.getString("endTime")
                binding.btnSubmit.setOnClickListener {
                    val reason = binding.etReason.text.toString()
                    if (reason.trim().isEmpty()) {
                        Toaster.show("请填写完整信息")
                        return@setOnClickListener
                    }
                    launch(tryBlock = {
                        progress.show()
                        StudentApi.postAttendanceAppeal(
                            NewAttendanceAppealDto(
                                courseId = intent.getBundleExtra("bundle")!!.getInt("courseId").toString(),
                                appealBeginTime = intent.getBundleExtra("bundle")!!
                                    .getString("beginTime")!!,
                                appealEndTime = intent.getBundleExtra("bundle")!!.getString("endTime")!!,
                                reason = reason,
                                status = "0"
                            )
                        )
                        Toaster.show("提交成功，请等待审核")
                        finish()
                    }, catchBlock = {
                        it.printStackTrace()
                        Toaster.show("提交失败")
                    }, finallyBlock = {
                        progress.dismiss()
                    })
                }
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.apply {
            title = if (!viewMode) "考勤申诉" else "申诉详情"
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        ImmersionBar.setTitleBar(this, binding.toolbar)


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