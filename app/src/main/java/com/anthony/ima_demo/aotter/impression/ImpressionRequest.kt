package com.anthony.ima_demo.aotter.impression

import androidx.annotation.IntRange

/**
 * 2020/06/08 created by Anthony Wu
 * 曝光參數提供者
 * 該類提供開發者設置客製化的顯示百分比與停留時間
 * 可以針對 visibleRangePercent 與 dwellSeconds  預設為 50% 與 5秒
 * 兩個參數決定 view 要顯示多少百分比在屏幕上才開始計算曝光，曝光的成立可由
 * dwellSeconds參數決定計算時長
 */

class ImpressionRequest {


    var visibleRangePercent = 50

    var dwellSeconds = 5

    /**
     * view顯示多少百分比後才算是在可見範圍
     * @param percent 該參數決定顯示該view多少百分比
     * 範圍限定是50％-100％
     */
    fun setVisibleRangePercent(@IntRange(from = 50, to = 100) percent: Int): ImpressionRequest {

        visibleRangePercent = percent

        return this
    }

    /**
     * view在可見範圍停留的秒數
     * @param seconds 停留的秒數
     */
    fun dwellSeconds(@IntRange(from = 5, to = 10) seconds: Int): ImpressionRequest {

        dwellSeconds = seconds

        return this
    }



}