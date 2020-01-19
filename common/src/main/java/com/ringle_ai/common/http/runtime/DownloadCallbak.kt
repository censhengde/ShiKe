package com.ringle_ai.common.http.runtime

import androidx.annotation.WorkerThread
import com.ringle_ai.common.http.ICallback

/**
 *create by 岑胜德
 *on 2019/11/4
 *说明:专门用于下载文件回调
 *
 */
abstract class DownloadCallbak : ICallback {

    /*
    * 在子线程运行
    * */
    @WorkerThread
    abstract fun onLoading(progress: Int)
}