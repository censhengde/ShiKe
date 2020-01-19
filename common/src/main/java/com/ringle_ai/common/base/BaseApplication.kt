package com.ringle_ai.common.base

import android.app.Application
import android.content.Context

open class BaseApplication: Application() {
    companion object{
    private lateinit var application:Context
        fun getAppContext():Context= application
    }

    override fun onCreate() {
        super.onCreate()
        application=this.applicationContext


    }

}