package com.ringle_ai.shike

import android.os.Build
import androidx.annotation.RequiresApi
import com.ringle_ai.common.base.BaseApplication
import com.ringle_ai.common.networklistener.NetworkManager

class App:BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        NetworkManager.init(this)
    }
}