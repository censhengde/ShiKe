package com.ringle_ai.common.app

import androidx.collection.ArrayMap
import java.lang.RuntimeException
import java.util.*
import kotlin.LazyThreadSafetyMode.*

object Configurator {
    //存储APP配置信息的map表
     val CONFIGS: ArrayMap<Any, Any> by lazy(NONE) { ArrayMap<Any,Any>() }

    init {
        //刚开始 配置是未准备好的
        CONFIGS.put(ConfigKeys.CONFIG_READY, false)
    }



    /*
    * 配置主机域名
    * */
    fun withApiHost(host: String): Configurator {
        this.CONFIGS.put(ConfigKeys.API_HOST, host)
        return this
    }

    /*通过key获取某一项配置信息
    * */
    fun <T> getConfiguration(@ConfigKeys.Keys key: Int): T? {
        if (!(CONFIGS.get(ConfigKeys.CONFIG_READY) as Boolean))
            throw RuntimeException("配置未准备好")
        return CONFIGS.get(key) as T
    }

    /*配置完成
    * */
    fun finish() {
        this.CONFIGS.put(ConfigKeys.CONFIG_READY, true)
    }

}