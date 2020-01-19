package com.ringle_ai.common.http

import android.util.ArrayMap
import com.ringle_ai.common.http.callback.IError
import com.ringle_ai.common.http.callback.IFailure
import com.ringle_ai.common.http.callback.IRquest
import com.ringle_ai.common.http.callback.ISuccess
import java.io.File

 class DownloadBuilder (val url : String,
                        val parmas: ArrayMap<String, Any>,
                        val success: ISuccess,
                        val error:IError,
                        val extention:String,
                        val fileName:String,
                        val file: File,
                        val downloadDir:String
                    ){
    //网络请求参数
    private lateinit var mParmas: ArrayMap<String, Any>
    //url
    private lateinit var mUrl: String

    private lateinit var mRequest: IRquest

    private lateinit var mSuccess: ISuccess

    private lateinit var mError: IError

    private lateinit var mIFailure: IFailure

    //上传下载文件
    private lateinit var mFile: File

    lateinit var mDownloadDir: String

    lateinit var mExtention: String

    lateinit var mFileName: String


    fun url(url: String): DownloadBuilder {
        this.mUrl = url
        return this
    }


    fun parmas(parmas:ArrayMap<String,Any>):DownloadBuilder{
        mParmas=parmas
        return this
    }

    fun success(success:ISuccess):DownloadBuilder{
        mSuccess=success
        return this

    }

    fun error(error: IError):DownloadBuilder{
        mError=error
        return this
    }


}