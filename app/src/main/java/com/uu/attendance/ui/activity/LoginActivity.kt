package com.uu.attendance.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseToolbarActivity
import com.uu.attendance.databinding.ActivityLoginBinding
import com.uu.attendance.model.network.api.AccountApi
import com.uu.attendance.util.KVUtil
import com.uu.attendance.util.LogUtil.Companion.debug

class LoginActivity : BaseToolbarActivity<ActivityLoginBinding>() {
    private val dialog by lazy {
        ProgressDialog(this).apply {
            setMessage("登录中...")
            setCancelable(false)
        }
    }
    override fun getToolbarTitle(): String {
        return "登录"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBg.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.spaceTop.layoutParams.apply {
                    height = (binding.ivBg.height * 0.125).toInt()
                    binding.spaceTop.layoutParams = this
                }
                binding.spaceAvatarUsername.layoutParams.apply {
                    height = (binding.ivBg.height * 0.1).toInt()
                    binding.spaceAvatarUsername.layoutParams = this
                }
                binding.spaceUsernamePassword.layoutParams.apply {
                    height = (binding.ivBg.height * 0.055).toInt()
                    binding.spaceUsernamePassword.layoutParams = this
                }
                binding.spacePasswordButton.layoutParams.apply {
                    height = (binding.ivBg.height * 0.055).toInt()
                    binding.spacePasswordButton.layoutParams = this
                }
                binding.ivBg.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        binding.btnLogin.setOnClickListener {
            launch (tryBlock = {
                dialog.show()
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                val loginResult = AccountApi.login(username, password)
                if (loginResult.code == 1) {
                    loginResult.data?.let {
                        debug(it)
//                        Toaster.show(loginResult.msg)
                        KVUtil.put("token", it.token)
                        KVUtil.put("id", it.id)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toaster.show(loginResult.msg)
                }
            }, catchBlock = {
                debug(it)
                Toaster.show("登录异常，请重试")
            }, finallyBlock = {
                dialog.dismiss()
            })
        }
    }
}