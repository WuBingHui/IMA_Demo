package com.anthony.ima_demo.aotter.impression

import android.graphics.Rect
import android.view.View


/**
 * 2020/06/09 created by Anthony Wu
 * view可見度百分比計算者
 * 該類是針對view在螢幕上可見百分比的計算
 */
 class ViewVisibilityPercentageCalculator {

    /**
     * 計算可見百分比
     * 主要調用 view 的 getLocalVisibleRect 函數，取得 rect 去做計算
     */
    fun getVisibilityPercents(currentView: View): Int {
        var percents = 100
        val rect = Rect()
        //防止出現view已不在可見得範圍之内仍然返回100（完全可見）
        val isVisible = currentView.getLocalVisibleRect(rect)
        if (isVisible) {
            //可見時做百分比的計算
            val height = currentView.measuredHeight
            if (viewIsPartiallyHiddenTop(rect)) {
                // view is partially hidden behind the top edge
                percents = (height - rect.top) * 100 / height
            } else if (viewIsPartiallyHiddenBottom(rect, height)) {
                percents = rect.bottom * 100 / height
            }
        } else {
            //View已经不可見
            percents = 0
        }
        return percents
    }

    //view底部部分不可見
    private fun viewIsPartiallyHiddenBottom(
        rect: Rect,
        height: Int
    ): Boolean {
        return rect.bottom in 1 until height
    }


    //view頂部部分不可見
    private fun viewIsPartiallyHiddenTop(rect: Rect): Boolean {
        return rect.top > 0
    }


}