package com.csd.kotlinapplication.util

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
 object LiveDataBus {
     //以任意字符串为Key，以MutableLiveData对象为Value的Map，以达到MutableLiveData对象复用目的
    private val bus: ArrayMap<Any, MutableLiveData<Any>> = ArrayMap()

     fun getChannel(key: Any): MutableLiveData<Any>? {
        if (!bus.containsKey(key)) bus.put(key, MutableLiveData())
            return bus.get(key)
    }
}