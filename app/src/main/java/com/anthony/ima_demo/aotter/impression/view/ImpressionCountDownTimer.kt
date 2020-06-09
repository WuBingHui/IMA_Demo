package com.anthony.ima_demo.aotter.impression.view

import android.os.CountDownTimer
import android.util.Log
import com.anthony.ima_demo.aotter.impression.ImpressionProvider


class ImpressionCountDownTimer(impressionRequest: ImpressionRequest) {

    private var startPercent = impressionRequest.visibleRangePercent

    private var MILLIS_IN_FEATURE = impressionRequest.dwellSeconds * 1000L

    private var countDownTimer: CountDownTimer? = null

    companion object {
        private const val COUNT_DOWN_INTERVAL = 1000L
    }


    fun checkPercent(percent: Int) {

        if (percent >= startPercent) {

            if(countDownTimer == null){

                countDownTimer = object : CountDownTimer(MILLIS_IN_FEATURE,COUNT_DOWN_INTERVAL) {
                    override fun onFinish() {

                    }

                    override fun onTick(millisUntilFinished: Long) {

                        Log.e("onTick", millisUntilFinished.toString())

                    }
                }

                countDownTimer?.start()

            }

        } else {

            countDownTimer?.cancel()

            countDownTimer = null

        }

    }

}