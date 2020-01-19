package com.ringle_ai.common.http

import com.ringle_ai.common.http.runtime.DownloadCallbak

/*
* 接口隔离层
*
* Client 端 HTTP请求统一接口
* */
object HttpHelper : IHttpProcessor {


    private lateinit var mProcessor: IHttpProcessor

    /*
   * 注册具体processor
   * */
    fun init(processor: IHttpProcessor) {
        mProcessor = processor
    }

    /*
    * GET请求
    * */
    override fun get(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback) {
        mProcessor.get(url, paramas, iCallback)
    }


    /*
    * POST请求
    * */
    override fun post(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback) {
        mProcessor.post(url, paramas, iCallback)
    }


    /*
    * 上传文件
    * */
    override fun upload(url: String, paramas: MutableMap<String, Any>, iCallback: ICallback) {
        mProcessor.upload(url, paramas, iCallback)
    }


    /*
    *
    * 下载文件
    * */
    override fun download(builder: DownloadBuilder,downloadCallbak: DownloadCallbak) {
        mProcessor.download(builder,downloadCallbak)

    }


}