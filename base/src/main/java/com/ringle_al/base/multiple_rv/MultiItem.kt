package com.ringle_al.base.multiple_rv

/**
 * create by 岑胜德
 * on 2019/11/14
 * 说明:所有Item的规范
 *
 */
interface MultiItem {
    fun getLayoutId(): Int
    fun getType(): Int
    fun convert(holder: MultiRVHolder)
}