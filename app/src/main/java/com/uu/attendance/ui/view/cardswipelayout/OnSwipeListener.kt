package com.uu.attendance.ui.view.cardswipelayout

import androidx.recyclerview.widget.RecyclerView

/**
 * @author yuqirong
 */
interface OnSwipeListener<T> {
    /**
     * 卡片还在滑动时回调
     *
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio      滑动进度的比例
     * @param direction  卡片滑动的方向，CardConfig.SWIPING_LEFT 为向左滑，CardConfig.SWIPING_RIGHT 为向右滑，
     * CardConfig.SWIPING_NONE 为不偏左也不偏右
     */
    fun onSwiping(viewHolder: RecyclerView.ViewHolder, ratio: Float, direction: Int)

    /**
     * 卡片完全滑出时回调
     *
     * @param viewHolder 该滑出卡片的viewHolder
     * @param t          该滑出卡片的数据
     * @param direction  卡片滑出的方向，CardConfig.SWIPED_LEFT 为左边滑出；CardConfig.SWIPED_RIGHT 为右边滑出
     */
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, t: T, direction: Int)

    /**
     * 所有的卡片全部滑出时回调
     */
    fun onSwipedClear()
}