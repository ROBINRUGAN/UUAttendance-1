package com.uu.attendance.ui.coursetable.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.flyjingfish.openimagelib.OpenImage
import com.flyjingfish.openimagelib.beans.OpenImageUrl
import com.flyjingfish.openimagelib.enums.MediaType
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.Toaster
import com.uu.attendance.base.ui.BaseActivity
import com.uu.attendance.databinding.ActivityNewAttendanceAppealBinding
import com.uu.attendance.model.network.api.StudentApi
import com.uu.attendance.util.URIPathHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class NewAttendanceAppealActivity : BaseActivity<ActivityNewAttendanceAppealBinding>() {
    private val progress by lazy {
        ProgressDialog(this).apply {
            setMessage("提交中...")
        }
    }

    private var appealId = -1
    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appealId = intent.getIntExtra("appealId", -1)
        if (appealId != -1) {
            binding.etReason.isEnabled = false
            binding.btnSubmit.visibility = android.view.View.GONE
            launch(tryBlock = {
                val data = StudentApi.getAttendanceAppealDetail(appealId).data!!
                binding.tvCourseName.text = data.courseName
                binding.tvBegintime.text = data.beginTime
                binding.tvEndtime.text = data.endTime
                binding.etReason.setText(data.reason)
                Glide.with(this@NewAttendanceAppealActivity).load(data.image).into(binding.ivImg)
                binding.ivImg.setOnClickListener {
                    OpenImage.with(this@NewAttendanceAppealActivity)
                        .setClickImageView(binding.ivImg)
                        .setSrcImageViewScaleType(ImageView.ScaleType.CENTER_CROP, true)
                        .setImageUrl(object : OpenImageUrl {
                            override fun getImageUrl() = data.image
                            override fun getVideoUrl() = ""
                            override fun getCoverImageUrl() = imageUrl
                            override fun getType() = MediaType.IMAGE
                        })
                        .setClickPosition(0)
                        .show()
                }
            }, catchBlock = {
                it.printStackTrace()
                Toaster.show("获取申诉详情失败")
            })
        } else {
            intent.getBundleExtra("bundle")?.let {
                binding.tvCourseName.text = it.getString("courseName")
                binding.tvBegintime.text = it.getString("beginTime")
                binding.tvEndtime.text = it.getString("endTime")
                binding.btnSubmit.setOnClickListener {
                    val reason = binding.etReason.text.toString()
                    if (reason.trim().isEmpty() || photoFile == null) {
                        Toaster.show("请填写完整信息")
                        return@setOnClickListener
                    }
                    launch(tryBlock = {
                        progress.show()
                        val body: RequestBody = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart(
                                "attendanceAppealImage",
                                photoFile!!.name,
                                RequestBody.create(
                                    "multipart/form-data".toMediaTypeOrNull(),
                                    photoFile!!
                                )
                            )
                            .addFormDataPart(
                                "courseId",
                                intent.getBundleExtra("bundle")!!.getInt("courseId").toString()
                            )
                            .addFormDataPart(
                                "appealBeginTime",
                                intent.getBundleExtra("bundle")!!.getString("beginTime")!!
                            )
                            .addFormDataPart(
                                "appealEndTime",
                                intent.getBundleExtra("bundle")!!.getString("endTime")!!
                            )
                            .addFormDataPart("reason", reason)
                            .addFormDataPart("status", "0")
                            .build()
                        val result = StudentApi.postAttendanceAppeal(body)
                        assert(result.code == 1)
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
            val pickMedia =
                registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: $uri")
                        binding.ivImg.setImageBitmap(
                            MediaStore.Images.Media.getBitmap(
                                contentResolver,
                                uri
                            )
                        )
                        photoFile = File(URIPathHelper.getPath(this, uri))
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
            binding.ivImg.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.apply {
            title = if (appealId == -1) "考勤申诉" else "申诉详情"
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