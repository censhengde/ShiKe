package com.ringle_ai.common.http.runtime

import com.alibaba.fastjson.JSON
import com.ringle_ai.common.http.ICallback
import com.ringle_ai.common.http.JsonCallback
import com.ringle_ai.common.http.analysisClassInfo

/*
* 专门用于解析JSon object格式数据
* */
@Suppress("UNCHECKED_CAST")
abstract class JsonObjectCallback<T> : JsonCallback {


    override fun onSuccess(result: String) {
        try {
            val clz = analysisClassInfo<T>(this)//解析泛型参数
            val res = JSON.parseObject(result, clz) as T
            onSuccess(res)
        } catch (e: Exception) {
            onFailure("Json 解析失败：${e.printStackTrace()}")
        }


    }

    override fun onFailure(err: String) {


    }

    override fun onFinish() {

    }

    abstract fun onSuccess(result: T)

}