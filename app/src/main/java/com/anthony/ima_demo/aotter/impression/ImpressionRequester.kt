package com.anthony.ima_demo.aotter.impression

import androidx.annotation.IntRange

class ImpressionRequester {

    var showVisibleRangePercent = 50

    var stopVisibleRangeSeconds = 5

    /**
     * view顯示多少百分比後才算是在可見範圍
     * @param percent 該參數決定顯示該view多少百分比
     * 範圍限定是50％-100％
     */
    fun setVisibleRangePercent(@IntRange(from = 50, to = 100) percent: Int): ImpressionRequester {

        showVisibleRangePercent = percent

        return this
    }

    /**
     * view在可見範圍停留的秒數
     * @param seconds 停留的秒數
     */
    fun setStopVisibleRangeSeconds(@IntRange(from = 5, to = 10) seconds: Int): ImpressionRequester {

        stopVisibleRangeSeconds = seconds

        return this
    }

}