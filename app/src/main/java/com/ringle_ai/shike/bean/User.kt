package com.ringle_ai.shike.bean

/**
 *create by 岑胜德
 *on 2019/11/6
 *说明:
 *
 */
data class User(var psw: String) {
   private var name: String
        set(value) {
            this.name = value
        }
        get() {
            return name
        }

    init {

    }
}