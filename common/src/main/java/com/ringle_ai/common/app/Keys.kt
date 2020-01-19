package com.ringle_ai.common.app

import androidx.annotation.IntDef

/*
* 标识信息类
* */
object ConfigKeys {
    //主机域名
    const val API_HOST: Int = 0
    //全局Context
    const val APPLICATION_CONTEXT: Int = 1
    //是否配置完成
    const val CONFIG_READY = 2

    /*
    * 自定义注解,限定取值*/
    @IntDef(API_HOST, APPLICATION_CONTEXT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Keys {}

}