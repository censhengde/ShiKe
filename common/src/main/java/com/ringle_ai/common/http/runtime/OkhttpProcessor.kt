package com.ringle_ai.common.http.runtime

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ringle_ai.common.http.DownloadBuilder
import com.ringle_ai.common.http.ICallback
import com.ringle_ai.common.http.IHttpProcessor
import com.ringle_ai.common.http.JsonCallback
import okhttp3.*
import java.util.concurrent.TimeUnit
import okhttp3.FormBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.*


/**
 *create by 岑胜德
 *on 2019/11/4
 *说明:Okhttp实际请求类
 *
 */
class OkhttpProcessor : IHttpProcessor {

    //OkHttpClient 统一配置
    private val mclient: OkHttpClient  by lazy {
        OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }
    //切换线程用
    private val mHander: Handler by lazy { Handler(Looper.getMainLooper()) }

   /*
   * get
   * */
    override fun get(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback) {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        asynRequest(request, iCallback)


    }


   /*
   * post
   * */
    override fun post(url: String, paramas: MutableMap<String, Any>, iCallback: JsonCallback) {
        val body = appendBody(paramas)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        asynRequest(request, iCallback)


    }

    /*
    * upload
    * */
    override fun upload(url: String, paramas: MutableMap<String, Any>, iCallback: ICallback) {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        paramas.forEach {
            if (it.value is File) {
                builder.addFormDataPart(
                    it.key,
                    (it.value as File).name,
                    RequestBody.create(null, it.value as File)
                    )
            } else {
                builder.addFormDataPart(it.key, it.value.toString())
            }

        }
        val body = builder.build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        mclient.newBuilder()
            .writeTimeout(50,TimeUnit.SECONDS)
            .build().newCall(request)
            .enqueue(object :Callback{

                override fun onFailure(call: Call, e: IOException) {
                    mHander.post { iCallback.onFailure("上传失败:${e.printStackTrace()}") }

                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful){
                    mHander.post { iCallback.onFailure("上传成功:${response.code()}") }
                    }else{
                    mHander.post { iCallback.onFailure("上传失败:${response.code()}") }
                    }

                }


            })

    }

    /*
    * download
    * */
    override fun download(builder: DownloadBuilder, downloadCallbak: DownloadCallbak) {
        val request = Request.Builder()
            .url(builder.url)
            .build()

        mclient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mHander.post { downloadCallbak.onFailure("下载失败") }
            }

            override fun onResponse(call: Call, response: Response) {
                val buf = ByteArray(2048)
                var len: Int = 0
                val file = File(builder.fileName, builder.extention)
                if (!file.exists()) {
                    file.mkdir()
                }
                val fis: InputStream
                val fos: FileOutputStream

                try {
                    fis = response.body()!!.byteStream()
                    fos = FileOutputStream(file)
                    val total = response.body()!!.contentLength()
                    var sum = 0
                    while (len != -1) {
                        len = fis.read(buf)
                        fos.write(buf, 0, len)
                        sum += len
                        val progress = (sum / total * 100).toInt()
                        downloadCallbak.onLoading(progress)

                    }
                    fos.flush()//清空fos管道数据
                    fis.close()
                    fos.close()
                    mHander.post { downloadCallbak.onSuccess("下载成功！") }


                } catch (e: Exception) {

                    mHander.post { downloadCallbak.onFailure("下载失败：${e.printStackTrace()}") }

                }


            }

        })
    }


    private fun asynRequest(request: Request, iCallback: JsonCallback) {
        iCallback.onStart()//开始
        mclient.newCall(request).enqueue(object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                mHander.post { iCallback.onFailure(e.toString()) }
            }

            override fun onResponse(call: Call, response: Response) {
                //切换主线程
                mHander.post {
                    iCallback.onFinish()//结束
                    val res = response.body()?.string()
                    if (response.isSuccessful && res != null) {
                        iCallback.onSuccess(res)
                    } else {
                        iCallback.onFailure("网络请求失败：${response.code()}")
                    }
                }

            }

        })
    }

    private fun appendBody(params: Map<String, Any>?): RequestBody {
        val body = FormBody.Builder()
        if (params == null || params.isEmpty()) {
            return body.build()
        }
        for (entry in params.entries) {

            body.add(entry.key, entry.value.toString())
        }
        Log.e("appendBody====>", body.toString())
        return body.build()
    }


}