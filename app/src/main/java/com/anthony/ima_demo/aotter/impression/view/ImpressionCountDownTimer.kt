package com.anthony.ima_demo.aotter.impression.view

import android.os.CountDownTimer
import com.anthony.ima_demo.aotter.impression.ImpressionProvider


class ImpressionCountDownTimer(impressionRequest: ImpressionRequest) {


    private var startPercent = impressionRequest.visibleRangePercent

    private var dwellSeconds = impressionRequest.dwellSeconds * 1000L

    private var countDownTimer: CountDownTimer? = null

    companion object {
        private const val MILLIS_IN_FEATURE = 1000L
    }

    fun startCountDown(percent: Int) {

        if (percent >= startPercent) {

            countDownTimer = object : CountDownTimer(MILLIS_IN_FEATURE, dwellSeconds) {
                override fun onFinish() {

                }

                override fun onTick(millisUntilFinished: Long) {

                }
            }

            countDownTimer?.start()

        } else {

            countDownTimer?.cancel()

        }

    }

}