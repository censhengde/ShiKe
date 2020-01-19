package com.ringle_ai.common.app

import android.app.Application
import android.content.Context


object ProjectInit {

    /*
    * 初始化`方法,需在Application onCreate()中调用
    * */
    fun init(application: Application): Configurator {
        Configurator.CONFIGS.put(ConfigKeys.APPLICATION_CONTEXT, application)
        return Configurator
    }

    /*
    * 获取全局Context
    * */
    fun getAppContext(): Context =
        Configurator.CONFIGS.get(ConfigKeys.APPLICATION_CONTEXT) as Context


}