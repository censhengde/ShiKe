package com.ringle_ai.common.http

import androidx.annotation.MainThread

/**
 *create by 岑胜德
 *on 2019/11/4
 *说明:专门用于json字符串请求回调
 *
 */
interface JsonCallback:ICallback {

    //网络请求开始
    @MainThread
    fun onStart()
    //请求结束
    @MainThread
    fun onFinish()
}