package com.ringle_ai.common.http

import com.ringle_ai.common.http.runtime.DownloadCallbak

interface IHttpProcessor {

    fun get(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback)

    fun post(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback)
    fun upload(url: String, paramas: MutableMap<String, Any>, iCallback: ICallback)
    fun download(builder: DownloadBuilder,downloadCallbak: DownloadCallbak)
}