package com.uu.attendance.ui.supervise.view.cardswipelayout

/**
 * @author yuqirong
 */
object CardConfig {

    /**
     * 显示可见的卡片数量
     */
    const val DEFAULT_SHOW_ITEM = 3

    /**
     * 默认缩放的比例
     */
    const val DEFAULT_SCALE = 0.1f

    const val DEFAULT_ALPHA = 0f

    /**
     * 卡片Y轴偏移量时按照14等分计算
     */
    const val DEFAULT_TRANSLATE_Y = 14

    const val DEFAULT_TRANSLATE_X = 12

    /**
     * 卡片滑动时默认倾斜的角度
     */
    const val DEFAULT_ROTATE_DEGREE = 15f

    /**
     * 卡片滑动时不偏左也不偏右
     */
    const val SWIPING_NONE = 1

    /**
     * 卡片向左滑动时
     */
    const val SWIPING_LEFT = 1 shl 2

    /**
     * 卡片向右滑动时
     */
    const val SWIPING_RIGHT = 1 shl 3

    /**
     * 卡片从左边滑出
     */
    const val SWIPED_LEFT = 1

    /**
     * 卡片从右边滑出
     */
    const val SWIPED_RIGHT = 1 shl 2
}