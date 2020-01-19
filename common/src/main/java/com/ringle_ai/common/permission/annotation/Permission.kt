package com.ringle_ai.common.permission.annotation

import com.ringle_ai.common.permission.PermissionUtil

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(
    val permissions: Array<String>,
    val requestCode: Int = PermissionUtil.DEFAULT_REQUEST_CODE
)