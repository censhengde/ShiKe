package com.ringle_ai.common.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BasePresenter: LifecycleObserver {


@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
protected fun onResum(){

}


}