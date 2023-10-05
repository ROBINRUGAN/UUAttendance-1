package com.uu.attendance.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseFragment
import com.uu.attendance.databinding.FragmentMeBinding
import com.uu.attendance.model.Identity
import com.uu.attendance.model.network.api.AccountApi
import com.uu.attendance.ui.activity.AttendanceAppealActivity
import com.uu.attendance.ui.activity.ChangePwdActivity
import com.uu.attendance.ui.activity.LeaveApplicationActivity
import com.uu.attendance.ui.activity.LoginActivity
import com.uu.attendance.ui.activity.RulesActivity
import com.uu.attendance.ui.viewmodel.MainViewModel
import com.uu.attendance.util.KVUtil
import com.uu.attendance.util.LogUtil.Companion.debug


class MeFragment : BaseFragment<FragmentMeBinding>() {

    lateinit var viewModel: MainViewModel

    companion object {
        val instance: MeFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.ivAvatar.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.itemLeaveapply.setOnClickListener {
            val intent = Intent(requireContext(), LeaveApplicationActivity::class.java)
            startActivity(intent)
        }

        binding.itemAppeal.setOnClickListener {
            val intent = Intent(requireContext(), AttendanceAppealActivity::class.java)
            startActivity(intent)
        }

        binding.itemRule.setOnClickListener {
            val intent = Intent(requireContext(), RulesActivity::class.java)
            startActivity(intent)
        }

        binding.itemChangePwd.setOnClickListener {
            val intent = Intent(requireContext(), ChangePwdActivity::class.java)
            intent.putExtra("userNo", viewModel.userInfo.value?.no)
            startActivity(intent)
        }

        binding.itemExit.setOnClickListener {
//            val intent = Intent(requireContext(), SuperviseDetailActivity::class.java) // debug
//            startActivity(intent)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("温馨提示")
                .setMessage("确定退出登录吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { dialog, _ ->
                    launch(tryBlock = {
                        AccountApi.logout()
                    }, catchBlock = {
                        debug(it)
                    })
                    KVUtil.put("token", "")
                    KVUtil.put("id", -1)
                    Toaster.show("已退出登录")
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .show()
        }

        if (KVUtil.get("token", "").isNotEmpty()) {
            launch(tryBlock = {
                viewModel.userInfo.value = AccountApi.getInfo().data!!
                viewModel.userInfo.value!!.let {
                    debug(it)
                    binding.tvName.text = it.name
                    binding.tvXueyuan.text = it.college
                    binding.tvId.text = it.no
                    binding.ivAvatar.setOnClickListener(null)
                }
            }, catchBlock = {
                debug(it)
                Toaster.show("获取用户信息异常，请重新登录")
            })
        }

    }

    override fun onResume() {
        super.onResume()
        setupHeaderMessage()
    }

    @SuppressLint("SetTextI18n")
    private fun setupHeaderMessage() {
        binding.identity.text = when (KVUtil.get("identity", Identity.STUDENT)) {
            Identity.STUDENT -> "学生"
            else -> "督导"
        }
        try {
            binding.tvTerm.text = viewModel.currentSemester.value.toString()
                .substring(0, 4) + " 年 " + viewModel.currentSemester.value.toString()
                .substring(5) + " 学期"
            binding.tvWeek.text = "第 ${viewModel.currentWeek.value} 周"
        } catch (ignore: Exception) {
        }
    }


}