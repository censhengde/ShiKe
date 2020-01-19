package com.ringle_ai.common.aop

/*
* 用于标识性能监控
* */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Trace(val tag:String="")