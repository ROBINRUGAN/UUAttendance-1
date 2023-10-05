package com.uu.attendance.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityChangePwdBinding
import com.uu.attendance.model.network.api.AccountApi
import com.uu.attendance.model.network.dto.ChangePwdDto
import com.uu.attendance.util.LogUtil.Companion.debug

class ChangePwdActivity : BaseActivity<ActivityChangePwdBinding>() {
    private var no: String = ""
    private val progress by lazy {
        ProgressDialog(this).apply {
            setMessage("正在修改...")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.apply {
            title = "修改密码"
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        ImmersionBar.setTitleBar(this, binding.toolbar)

        no = intent.getStringExtra("userNo") ?: "未登录"
        binding.etUsername.setText(no)

        binding.btnSubmit.setOnClickListener {
            val oldPwd = binding.etOldPwd.text.toString()
            val newPwd = binding.etNewPwd.text.toString()
            if (oldPwd.trim().isEmpty() || newPwd.trim().isEmpty()) {
                Toaster.show("请填写完整信息")
                return@setOnClickListener
            }
            val dto = ChangePwdDto(no, oldPwd, newPwd)
            launch(tryBlock = {
                progress.show()
                val result = AccountApi.changePwd(dto)
                Toaster.show(result.msg)
                if (result.code == 1) finish()
            }, catchBlock = {
                debug(it)
                Toaster.show("修改失败")
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