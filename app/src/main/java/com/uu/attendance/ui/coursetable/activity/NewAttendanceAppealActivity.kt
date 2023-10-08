package com.uu.attendance.ui.coursetable.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityNewAttendanceAppealBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.model.network.dto.NewAttendanceAppealDto

class NewAttendanceAppealActivity : BaseActivity<ActivityNewAttendanceAppealBinding>() {
    private val progress by lazy {
        ProgressDialog(this).apply {
            setMessage("提交中...")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getBundleExtra("bundle")?.let {
            binding.tvCourseName.text = it.getString("courseName")
            binding.tvBegintime.text = it.getString("beginTime")
            binding.tvEndtime.text = it.getString("endTime")
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.apply {
            title = "考勤申诉"
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        ImmersionBar.setTitleBar(this, binding.toolbar)


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