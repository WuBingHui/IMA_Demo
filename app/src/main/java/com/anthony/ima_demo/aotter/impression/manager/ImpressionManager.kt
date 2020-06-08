package com.anthony.ima_demo.aotter.impression.manager

import android.content.Context
import android.view.View
import androidx.annotation.Nullable
import com.anthony.ima_demo.aotter.impression.ImpressionRequester


class ImpressionManager {

    private lateinit var impressionRequester: ImpressionRequester

    private lateinit var context: Context

    fun with(@Nullable view: View): ImpressionManager {

        context = view.context

        return this

    }

    fun impressionRequester(@Nullable impressionRequester: ImpressionRequester): ImpressionManager {

        this.impressionRequester = impressionRequester

        return this
    }


    fun apply(){



    }





}


