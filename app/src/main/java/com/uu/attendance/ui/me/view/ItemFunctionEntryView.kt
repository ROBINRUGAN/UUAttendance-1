package com.uu.attendance.ui.me.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.uu.attendance.R

class ItemFunctionEntryView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    lateinit var tvName: TextView
    lateinit var ivIcon: ImageView

    private fun init(attrs: AttributeSet?) {
        val view = LayoutInflater.from(context).inflate(R.layout.item_function_entry, this)
        tvName = view.findViewById(R.id.tv_name)
        ivIcon = view.findViewById(R.id.iv_icon)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.ItemFunctionEntryView
        )
        typedArray.getResourceId(R.styleable.ItemFunctionEntryView_android_src, 0).let {
            if (it != 0) {
                ivIcon.setImageResource(it)
            }
        }
        typedArray.getString(R.styleable.ItemFunctionEntryView_android_text)?.let {
            tvName.text = it
        }
        typedArray.recycle()
    }

}