package com.ringle_ai.shike.ui.activity

import android.os.Bundle
import com.ringle_ai.common.base.BaseActivity
import com.ringle_ai.shike.R

/**
 *create by 岑胜德
 *on 2019/11/1
 *说明:
 *
 */
class SplashActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }


    override fun onResume() {
        super.onResume()
        toActivity(MainActivity::class.java)
        finish()
    }

}