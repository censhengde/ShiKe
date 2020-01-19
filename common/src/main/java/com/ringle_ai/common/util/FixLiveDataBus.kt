package com.csd.kotlinapplication.util

import android.util.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.Exception

/*
* 修复版LiveDataBus：拦截第一次数据发送
* 例如：从A Activity发送数据到 B Activity时 B Activity第一次
* 走到onResum()方法将不会收到数据,之后再调用setValue()/postValue()发送才能收到数据
* */
 object FixLiveDataBus {
    private val bus: ArrayMap<Any, FixMutableLiveData<Any>> = ArrayMap()

    fun getChannel(key: Any): MutableLiveData<Any>? {
         if (!bus.containsKey(key)) bus.put(key, FixMutableLiveData<Any>())
         return bus.get(key)
    }
     /*
     扩展MutableLiveData
     * */
  private  class FixMutableLiveData<T> : MutableLiveData<T>() {
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            //hook切入点，目标：将 mLastVersion值修改为 mVersion 值
            hook(observer)
        }


        private fun hook(observer: Observer<in T>) = try {
            /*======================拿到mLastVersion=====================================*/
            //拿到LiveData中的mObservers字段（SafeIterableMap 类型）
            val liveDataClz = LiveData::class.java
            val mObserverField = liveDataClz.getDeclaredField("mObservers")
            mObserverField.isAccessible = true
            //拿到FixMutableLiveData当前实例的 mObservers 的值（即当前mObservers的 SafeIterableMap 实例）
            val mObserversObject = mObserverField.get(this)
            //通过mObserversObject拿到 SafeIterableMap 的Class对象
            val mObserversObjectClz = mObserversObject.javaClass
            //通过SafeIterableMap 的Class对象拿到get方法对象
            val mapGetMethod = mObserversObjectClz.getDeclaredMethod("get", Any::class.java)
            mapGetMethod.isAccessible = true
            //执行get方法，得到返回值是SafeIterableMap的一个Entry
            //参数1：驱动此方法的实例。参数2：传入此方法的参数
            val entry = mapGetMethod.invoke(mObserversObject, observer)
            //拿到 LifecycleBoundObserver 实例
            val lifecycleBoundObserver = (entry as Map.Entry<*, *>).value
            //通过 lifecycleBoundObserver 拿到 ObserverWrapper 的Class 对象
            val observerWrapperClz = (lifecycleBoundObserver?.javaClass as Class<*>).superclass
            //拿到 mLastVersion 字段
            val mLastVersion = observerWrapperClz?.getDeclaredField("mLastVersion")
            mLastVersion?.isAccessible = true

          /*======================拿到 mVersion=====================================*/
            //拿到 mVersion 字段
            val mVersion = liveDataClz.getDeclaredField("mVersion")
            mVersion.isAccessible = true

            //拿到当前实例的 mVersion 值
            val mVersionValue = mVersion.get(this)
            //设置 lifecycleBoundObserver 的 mLastVersion 值
            mLastVersion?.set(lifecycleBoundObserver, mVersionValue)

        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}