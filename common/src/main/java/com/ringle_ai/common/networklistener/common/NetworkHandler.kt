package com.ringle_ai.common.networklistener.common


/**
 *create by 岑胜德
 *on 2019/10/30
 *说明:网络传递者,中间层,观察者与被观察者之间通信必须通过这一层
 *
 */
abstract class NetworkHandler {

    abstract fun registerObserver(observer: Any)//注册观察者

    abstract fun unRegisterObserver(observer: Any)//取消注册

    abstract fun getCurrentNetType(): NetType//获取当前网络状态

    abstract fun handleState(netType: NetType)//传递网络状态给观察者




}