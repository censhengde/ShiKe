package com.ringle_al.base.recyclerview

import android.view.View
import java.text.FieldPosition

/**
 *create by 岑胜德
 *on 2019/11/12
 *说明:
 *
 */
interface ItemListener<T> {

    fun onItemClick(view: View, entity: T, position: Int)


    fun onItemLongClick(view: View, entity: T, position: Int)
}