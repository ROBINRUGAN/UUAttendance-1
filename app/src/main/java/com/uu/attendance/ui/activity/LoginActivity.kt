package com.uu.attendance.ui.activity

import android.os.Bundle
import android.view.ViewTreeObserver
import com.uu.attendance.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

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
    }
}