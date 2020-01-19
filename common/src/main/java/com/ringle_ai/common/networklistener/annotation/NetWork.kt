package com.ringle_ai.common.networklistener.annotation

import com.ringle_ai.common.networklistener.common.NetType


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NetWork(val netType: NetType = NetType.NONE)