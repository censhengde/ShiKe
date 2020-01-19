package com.ringle_ai.shike.ui.activity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.TextView
import com.ringle_ai.common.aop.Trace
import com.ringle_ai.common.base.BaseActivity
import com.ringle_ai.common.networklistener.NetworkManager
import com.ringle_ai.common.networklistener.annotation.NetWork
import com.ringle_ai.common.networklistener.common.NetType
import com.ringle_ai.common.permission.annotation.Permission
import com.ringle_ai.common.permission.annotation.PermissionCanceled
import com.ringle_ai.common.permission.annotation.PermissionDenied
import com.ringle_ai.shike.R
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //注册
        NetworkManager.registerObserver(this)
        tv = findViewById(R.id.tv)
    }

    fun onClick(view: View) {
        permissionRequest()
    }

    fun onClick01(view: View) {
        behavior()
    }

    /*
    * 网络框架规定注解方法必须是返回值为void,参数为NetType类型
    *任意网络
    **/
    @SuppressLint("SetTextI18n")
    @NetWork(NetType.AUTO)
    fun netWork_Auto(netType: NetType) {
        Log.e(TAG,"当前网络AUTO:${netType}")
        tv.setText("当前网络AUTO:$netType")
    }

    /*
    *WIFI
    */
    @NetWork(NetType.WIFI)
    fun netWork_Wifi(netType: NetType) {
        Log.e(TAG, "当前网络:${netType}")
        tv.setText("当前网络:$netType")
    }

    /*
    * 移动流量
    */
    @NetWork(NetType.GPRS)
    fun netWork_Gprs(netType: NetType) {
        Log.e(TAG, "当前网络:${netType}")
        tv.setText("当前网络:$netType")
    }

    /*
    * 无网络
    *
    * */
    @NetWork(NetType.NONE)
    fun netWork_None(netType: NetType) {
        Log.e(TAG, "当前网络:${netType}")
        tv.setText("当前无网络:$netType")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    /*
    * 权限请求
    *点击同意授权后回调到这里
    * */
    @Permission(arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE))
    fun permissionRequest() {
        Log.e(TAG, "授权成功")
        toast("授权成功!")
    }


    @PermissionCanceled
    fun permissionCancelded() {
        Log.e(TAG, "取消授权")
        toast("取消授权")
    }


    /*
    *取消授权,并点击了不再提示
    *此时根据业务需求引导用户打开设置页面手动授权
    *(只要有一个权限点击了不再提示都会回调到这里,这时应该
    *引导用户打开设置页面进行手动授权）
    * */
    @PermissionDenied
    fun permissionDenied() {
        Log.e(TAG, "取消授权,不再提示")
        toast("取消授权,不再提示")
    }

    @Trace("性能统计")
    fun behavior() {
        SystemClock.sleep(1000)//模拟耗时
    }


    override fun onDestroy() {
        super.onDestroy()
        //注销
        NetworkManager.unRegister(this)
    }
}
