package com.ringle_ai.common.util

import android.os.Looper

/**
 *create by 岑胜德
 *on 2019/10/31
 *说明:
 *
 */

fun isMainThread():Boolean=Looper.getMainLooper()== Looper.myLooper()