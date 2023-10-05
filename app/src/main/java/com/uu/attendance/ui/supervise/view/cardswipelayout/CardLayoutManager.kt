package com.uu.attendance.ui.supervise.view.cardswipelayout

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

/**
 * @author yuqirong
 */
class CardLayoutManager(recyclerView: RecyclerView, itemTouchHelper: ItemTouchHelper) :
    RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = itemCount
        // 当数据源个数大于最大显示数时
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (position in CardConfig.DEFAULT_SHOW_ITEM downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                // recyclerview 布局
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )
                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    // 和最后一个重叠
                    view.scaleX = 1 - (position - 1) * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - (position - 1) * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        ((position - 1) * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y).toFloat()
                    view.translationX = ((position - 1) * view.measuredWidth / CardConfig.DEFAULT_TRANSLATE_X).toFloat()
                    view.alpha = 1 - (position - 1) * CardConfig.DEFAULT_ALPHA
                } else if (position > 0) {
                    view.scaleX = 1 - position * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (position * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y).toFloat()
                    view.translationX = (position * view.measuredWidth / CardConfig.DEFAULT_TRANSLATE_X).toFloat()
                    view.alpha = 1 - position * CardConfig.DEFAULT_ALPHA
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时
            for (position in itemCount - 1 downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                // recyclerview 布局
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )
                if (position > 0) {
                    view.scaleX = 1 - position * CardConfig.DEFAULT_SCALE
                    view.scaleY = 1 - position * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (position * view.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y).toFloat()
                    view.translationX = (position * view.measuredWidth / CardConfig.DEFAULT_TRANSLATE_X).toFloat()
                    view.alpha = 1 - position * CardConfig.DEFAULT_ALPHA
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val mOnTouchListener = OnTouchListener { v, event ->
        val childViewHolder = recyclerView.getChildViewHolder(v)
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            itemTouchHelper.startSwipe(childViewHolder)
        }
        false
    }

}