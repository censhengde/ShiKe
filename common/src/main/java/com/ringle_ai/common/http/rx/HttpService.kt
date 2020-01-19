package com.ringle_ai.common.http.rx

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

/*
*
*
* */
interface HttpService {

    @GET
    fun get(@Url url:String,@QueryMap parmas :MutableMap<String,Any>) : Observable<String>

    @FormUrlEncoded
    @POST
    fun post(@Url url:String,@FieldMap parmas :MutableMap<String,Any>) : Observable<String>

    /*
    * 上传
    * */
    @Multipart
    @POST
    fun upload(@Url url: String,@Part file :MultipartBody.Part):Observable<String>

    /*
    * 下载
    * */
    @Streaming
    @GET
    fun download(@Url url: String,@QueryMap parmas: MutableMap<String, Any>):Observable<ResponseBody>
}