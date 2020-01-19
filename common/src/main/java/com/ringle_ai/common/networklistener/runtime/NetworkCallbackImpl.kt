package com.ringle_ai.common.networklistener.runtime

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import com.ringle_ai.common.networklistener.common.NetType
import com.ringle_ai.common.networklistener.common.NetworkOwner
import com.ringle_ai.common.networklistener.common.NetworkHandler
import com.ringle_ai.common.networklistener.util.getNetType
import com.ringle_ai.common.util.isMainThread

internal class NetworkCallbackImpl(val application: Application) :
    ConnectivityManager.NetworkCallback(),
    NetworkOwner {


    override fun getNetworkHandler(): NetworkHandler = mHandlerHandler

    companion object {
        private var isDebug = true
        private const val TAG = "NetworkCallbackImpl===>"

        private const val WIFI=0
        private const val GPRS=1
        private const val AUTO=2
        private const val NONE=3
    }

    private var isFirst: Boolean = true//是否第一次启动网络监听

    private lateinit var lastNetWork: Network

    private var hasNewNetwork: Boolean = false//是否有新网络开启

    private lateinit var mNetworkCapabilities: NetworkCapabilities


    private lateinit var manager: ConnectivityManager

    private val mHandlerHandler: NetworkHandler = NetworkHandlerRegistry()

    private val mHandler=Handler(Looper.getMainLooper())


    /*
    * 初始化
    * */
    init {

        //android 5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("NetWork", "初始化")
            val request =
                NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build()
            manager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //参数3:让回调通过Handler切换到主线程
//            manager.registerNetworkCallback(request, this, Handler(Looper.getMainLooper()))//此句存在兼容性问题 需要26水平api
            manager.registerNetworkCallback(request,this)

        }
    }

    /*
    * 实时网络状态改变回调(WIFI网络信号格数变化也会回调)
    * n注意:该方法回调频繁,应避免重量级操作
    * 尽可能拦截些不必要回调
    * */
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        mNetworkCapabilities = networkCapabilities
        Log.e(TAG, "网络状态改变")
        //网络不可用
        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            return
        }

        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            if (!hasNewNetwork) return
            hasNewNetwork = false
            Log.e(TAG, "网络状态:wifi")
            mHandler.post { mHandlerHandler.handleState(NetType.WIFI) }


        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            if (!hasNewNetwork) return
            hasNewNetwork = false
            Log.e(TAG, "网络状态:gprs")
          mHandler.post { mHandlerHandler.handleState(NetType.GPRS)}

        }

    }


    /*
    * 网络连接
    * */
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        hasNewNetwork = true
        Log.e(TAG, "网络连接...是否主线程:${isMainThread()}")
        lastNetWork = network
        //只允许第一次执行
        if (!isFirst) return
        isFirst = false
        when (getNetType()) {

            NetType.WIFI -> mHandler.post { mHandlerHandler.handleState(NetType.WIFI) }

            NetType.AUTO -> mHandler.post { mHandlerHandler.handleState(NetType.AUTO) }

            NetType.GPRS -> mHandler.post { mHandlerHandler.handleState(NetType.GPRS) }

            NetType.NONE -> mHandler.post { mHandlerHandler.handleState(NetType.NONE) }
        }
    }

    /*
    * 网络断开
    *
    * */

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.e(TAG, "网络断开...是否主线程:${isMainThread()}")
        /*
        * 考虑到从移动流量(GPRS)切换到WIFI时,程序流程是先执行onAvailable创建了新的Network,
        * 再执行onLost,此时回调Network对象是旧的,所以要拦截一次.如果此次回调的Network是旧的,
        * 意味着没有新的Network生成,也就表示将来没有了可用网络,这才进入无网络状态
        * */
        if (lastNetWork == network){

            mHandler.post { mHandlerHandler.handleState(NetType.NONE) }

        }
    }

    override fun onUnavailable() {
        super.onUnavailable()
        Log.e(TAG, "网络连接异常...是否主线程:${isMainThread()}")
    }


    //
//    fun getNetType(): NetType {
//
//        if (mNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return NetType.WIFI
//        if (mNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return NetType.GPRS
//        return NetType.NONE
//
//    }


}