package com.uu.attendance.ui.view

import android.graphics.Canvas
import android.graphics.Path
import androidx.appcompat.widget.AppCompatImageView

class RoundImageView: AppCompatImageView {
    constructor(context: android.content.Context) : super(context) {
        AppCompatImageView(context)
    }
    constructor(context: android.content.Context, attrs: android.util.AttributeSet?) : super(context, attrs) {
        AppCompatImageView(context, attrs)
    }
    constructor(context: android.content.Context, attrs: android.util.AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        AppCompatImageView(context, attrs, defStyleAttr)
    }

    // ShapeableImageView 亦可实现此效果
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        val path = Path()
        path.addCircle(
            (width / 2).toFloat(), (height / 2).toFloat(), (width.coerceAtMost(height) / 2).toFloat(),
            Path.Direction.CCW
        )
        canvas.clipPath(path)
        super.onDraw(canvas)
        canvas.restore()
    }
}