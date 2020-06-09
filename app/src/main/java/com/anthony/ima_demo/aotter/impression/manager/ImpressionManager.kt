package com.anthony.ima_demo.aotter.impression.manager

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import com.anthony.ima_demo.aotter.impression.ImpressionRequester
import com.anthony.ima_demo.aotter.impression.ViewVisibilityPercentageCalculator


/**
 * 2020/06/09 created by Anthony Wu
 * 曝光管理者
 * 該類是廣告曝光的核心類
 * 只接收view 並把 view 配發給其他職責的類做各自的職責
 * 並提供曝光成立與不成立的callback
 */
class ImpressionManager {

    private lateinit var impressionRequester: ImpressionRequester

    private var viewVisibilityPercentageCalculator: ViewVisibilityPercentageCalculator =
        ViewVisibilityPercentageCalculator()

    private lateinit var context: Context

    private lateinit var view:View

    fun with(@Nullable view: View): ImpressionManager {

        context = view.context

        this.view = view

        return this

    }

    fun impressionRequester(@Nullable impressionRequester: ImpressionRequester): ImpressionManager {

        this.impressionRequester = impressionRequester

        return this
    }


    fun apply(){


        val rect =  Rect()

        view.getLocalVisibleRect(rect)

        view.viewTreeObserver.addOnScrollChangedListener  {

            Log.e("view","${view.tag}-${viewVisibilityPercentageCalculator.getVisibilityPercents(view)}%")

        }


    }

}


