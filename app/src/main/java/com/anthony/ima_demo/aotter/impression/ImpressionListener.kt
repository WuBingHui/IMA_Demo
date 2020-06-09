package com.anthony.ima_demo.aotter.impression




interface ImpressionListener {

    fun onImpressionSuccess()

    fun onImpressionFail()

    fun currentImpressionPercent(percent:Int)

}