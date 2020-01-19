package com.ringle_ai.common.http.util

import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import com.ringle_ai.common.app.ProjectInit
import java.io.File

/**
 *create by 岑胜德
 *on 2019-10-29
 *说明:
 *
 */

private const val TIME_FORMAT = "_yyyyMMdd_HHmmss"

private val SDCARD_DIR = Environment.getExternalStorageDirectory().path//SD卡根目录


//系统相机目录
private val CAMERA_PHOTO_DIR =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        .path + "/Camera/"

/*
* 获取文件扩展名
*
* */
fun getExtension(filePath: String): String {

    val file = File(filePath)

    val name = file.name

    val idx = name.lastIndexOf('.')

    if (idx > 0) {
        return name.substring(idx + 1)
    }
    return ""
}

/*
* 刷新系统相册
* */
fun refreshDCIM() {
    MediaScannerConnection.scanFile(
        ProjectInit.getAppContext(),
        arrayOf(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .path
        ), null, null
    )
}