package com.ringle_ai.common.http.runtime

import com.alibaba.fastjson.JSON
import com.ringle_ai.common.http.ICallback
import com.ringle_ai.common.http.JsonCallback
import com.ringle_ai.common.http.analysisClassInfo

/*
* 专门用于解析JSon array格式数据
* */
abstract class JsonArrayCallback<T> : JsonCallback {

    override fun onSuccess(result: String) = try {
        val clz=analysisClassInfo<T>(this)
        val resList= JSON.parseArray(result,clz)
        onSuccess(resList)
    } catch (e: Exception) {
        onFailure("JsonArray 解析失败：${e.printStackTrace()}")
    }

    override fun onFailure(err: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    abstract fun onSuccess(resultList: List<T>)

}