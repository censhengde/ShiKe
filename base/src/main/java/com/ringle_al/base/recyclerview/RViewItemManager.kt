package com.ringle_al.base.recyclerview

import androidx.collection.SparseArrayCompat
import java.lang.RuntimeException
import java.text.FieldPosition

/**
 *create by 岑胜德
 *on 2019/11/12
 *说明:
 *
 */
class RViewItemManager<T> {
    //key:viewType,value:RViewItem<T>?
    private val styles = SparseArrayCompat<RViewItem<T>>()

    fun addStyles(item: RViewItem<T>?) {
        item ?: return

        styles.put(item.getType(), item)


    }

    //获取item种类数量
    fun getItemStyleCount(): Int = styles.size()

    fun getItemViewType(entity: T, position: Int): Int {
        for (i: Int in styles.size() downTo 0) {
            val item = styles.valueAt(i)
            if (item.isItemView(entity, position)) {
                return styles.keyAt(i)
            }
        }
        throw RuntimeException("没有匹配")
    }

    fun getRViewItem(viewType: Int): RViewItem<T> {
        return styles.get(viewType) ?: throw RuntimeException("未找到RViewItem")
    }

    fun convert(holder: RViewHolder, entity: T, position: Int) {
        for (i: Int in styles.size() downTo 0) {
            val item = styles.valueAt(i)
            if (item.isItemView(entity, position)) {
                item.convert(holder, entity, position)
                return
            }

        }
    }
}