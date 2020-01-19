package com.ringle_ai.common.permission.annotation
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PermissionDenied(val requestCode:Int=0xAbc1994)