package com.ringle_al.base.recyclerview

/**
 *create by 岑胜德
 *on 2019/11/12
 *说明:多样式item的抽象
 *
 */
interface RViewItem<T> {

    fun setItemLayout():Int

    fun getType():Int

    fun openClick():Boolean

    fun isItemView(entity:T,position:Int):Boolean

    //让holder与entity数据绑定:如holder.tv.setText(entity.getXXX())
    fun convert(holder: RViewHolder, entity: T, position: Int)


}