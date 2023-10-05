package com.uu.attendance.ui.supervise.view.cardswipelayout

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yuqirong
 */
class CardItemTouchHelperCallback<T> : ItemTouchHelper.Callback {
    private val adapter: RecyclerView.Adapter<*>
    private var dataList: MutableList<T>
    private var mListener: OnSwipeListener<T>? = null

    constructor(adapter: RecyclerView.Adapter<*>, dataList: MutableList<T>) {
        this.adapter = checkIsNull(adapter)
        this.dataList = checkIsNull(dataList)
    }

    constructor(
        adapter: RecyclerView.Adapter<*>,
        dataList: MutableList<T>,
        listener: OnSwipeListener<T>?
    ) {
        this.adapter = checkIsNull(adapter)
        this.dataList = checkIsNull(dataList)
        mListener = listener
    }

    private fun <T> checkIsNull(t: T?): T {
        if (t == null) {
            throw NullPointerException()
        }
        return t
    }

    fun setOnSwipedListener(mListener: OnSwipeListener<T>) {
        this.mListener = mListener
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        var swipeFlags = 0
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is CardLayoutManager) {
            swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // 移除 onTouchListener,否则触摸滑动会乱了
        viewHolder.itemView.setOnTouchListener(null)
        val layoutPosition = viewHolder.layoutPosition
        val remove: T = dataList.removeAt(layoutPosition)
        adapter.notifyItemRemoved(layoutPosition)
        if (mListener != null) {
            mListener!!.onSwiped(
                viewHolder,
                remove,
                if (direction == ItemTouchHelper.LEFT) CardConfig.SWIPED_LEFT else CardConfig.SWIPED_RIGHT
            )
        }
        // 当没有数据时回调 mListener
        if (adapter.itemCount == 0) {
            if (mListener != null) {
                mListener!!.onSwipedClear()
            }
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var ratio = dX / getThreshold(recyclerView, viewHolder)
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1f
            } else if (ratio < -1) {
                ratio = -1f
            }
            itemView.rotation = ratio * CardConfig.DEFAULT_ROTATE_DEGREE
            val childCount = recyclerView.childCount
            // 当数据源个数大于最大显示数时
            if (childCount > CardConfig.DEFAULT_SHOW_ITEM) {
                for (position in 1 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (index - Math.abs(ratio)) * itemView.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                    view.translationX =
                        (index - Math.abs(ratio)) * itemView.measuredWidth / CardConfig.DEFAULT_TRANSLATE_X
                    view.alpha = 1 - index * CardConfig.DEFAULT_ALPHA + Math.abs(ratio) * CardConfig.DEFAULT_ALPHA
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (position in 0 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE
                    view.translationY =
                        (index - Math.abs(ratio)) * itemView.measuredHeight / CardConfig.DEFAULT_TRANSLATE_Y
                    view.translationX =
                        (index - Math.abs(ratio)) * itemView.measuredWidth / CardConfig.DEFAULT_TRANSLATE_X
                    view.alpha = 1 - index * CardConfig.DEFAULT_ALPHA + Math.abs(ratio) * CardConfig.DEFAULT_ALPHA
                }
            }
            if (mListener != null) {
                if (ratio != 0f) {
                    mListener!!.onSwiping(
                        viewHolder,
                        ratio,
                        if (ratio < 0) CardConfig.SWIPING_LEFT else CardConfig.SWIPING_RIGHT
                    )
                } else {
                    mListener!!.onSwiping(viewHolder, ratio, CardConfig.SWIPING_NONE)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.rotation = 0f
    }

    private fun getThreshold(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Float {
        return recyclerView.width * getSwipeThreshold(viewHolder)
    }
}