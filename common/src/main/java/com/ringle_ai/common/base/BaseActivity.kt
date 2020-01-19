package com.ringle_ai.common.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ringle_ai.common.mvp.BasePresenter


abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = javaClass.simpleName
    open fun setPresenter(presenter: BasePresenter) {
        lifecycle.addObserver(presenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun toActivity(targetClz: Class<out Activity>) {
        startActivity(Intent(this, targetClz))
    }


}