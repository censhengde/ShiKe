package com.ringle_ai.common.permission

interface IPermission {
    /*
    * 已经授权
    * */
    fun granted()


    /*
    * 取消授权
    * */
    fun canceled()

    /*
    * 被拒绝,点击了不再提示
    * */

    fun denied()
}