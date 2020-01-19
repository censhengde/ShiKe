package com.ringle_ai.common

import android.util.ArrayMap
import com.ringle_ai.common.bean.HttpGet
import com.ringle_ai.common.http.HttpHelper
import com.ringle_ai.common.http.JsonCallback
import com.ringle_ai.common.http.runtime.JsonObjectCallback
import com.ringle_ai.common.http.runtime.OkhttpProcessor
import org.junit.Test

/**
 *create by 岑胜德
 *on 2019/11/5
 *说明:
 *
 */
class HttpTest {
    init {
        HttpHelper.init(OkhttpProcessor())
    }

    @Test
    fun getTest(){
        val url="http://www." +
                ""
        val parms= ArrayMap<String,Any>()

        HttpHelper.get(url,parms,object :JsonObjectCallback<HttpGet>(){
            override fun onStart() {


            }


            override fun onSuccess(result: HttpGet) {


            }

            override fun onFailure(err: String) {

            }


        })

    }


}