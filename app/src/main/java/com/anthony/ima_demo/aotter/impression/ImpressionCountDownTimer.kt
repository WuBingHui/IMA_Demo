package com.anthony.ima_demo.aotter.impression

import android.os.CountDownTimer
import android.util.Log
import com.anthony.ima_demo.aotter.impression.ImpressionRequest


class ImpressionCountDownTimer(impressionRequest: ImpressionRequest,private val impressionListener:ImpressionListener?) {

    private var startPercent = impressionRequest.visibleRangePercent

    private var dwellSeconds = impressionRequest.dwellSeconds * 1000L

    private var countDownTimer: CountDownTimer? = null

    private var isCountDown = false

    companion object {
        private const val COUNT_DOWN_INTERVAL = 1000L
    }


    fun checkPercent(percent: Int) {

        if (percent >= startPercent) {

            if (countDownTimer == null) {
                countDownTimer = object : CountDownTimer(
                    dwellSeconds,
                    COUNT_DOWN_INTERVAL
                ) {
                    override fun onFinish() {

                        impressionListener?.onImpressionSuccess()

                    }

                    override fun onTick(millisUntilFinished: Long) {

                        Log.e("onTick", millisUntilFinished.toString())

                    }
                }
            }

            if (!isCountDown) {
                isCountDown = true
                countDownTimer?.start()
            }

        } else {

            stop()

        }

    }

    fun stop() {

        countDownTimer?.cancel()

        isCountDown = false

        countDownTimer = null


    }


}