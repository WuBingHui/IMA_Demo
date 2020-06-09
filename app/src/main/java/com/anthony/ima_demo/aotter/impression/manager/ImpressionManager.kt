package com.anthony.ima_demo.aotter.impression.manager

import android.content.Context
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.anthony.ima_demo.aotter.impression.ImpressionProvider


/**
 * 2020/06/09 created by Anthony Wu
 * 曝光管理者
 * 該類是廣告曝光的核心類
 * 只接收view 並把 view 配發給其他職責的類做各自的職責
 * 並提供曝光成立與不成立的callback
 */
class ImpressionManager {


    private lateinit var context: Context


    fun with(@Nullable view: View,lifecycle: Lifecycle): ImpressionProvider {

        context = view.context

        return ImpressionProvider(view,lifecycle)

    }




}


