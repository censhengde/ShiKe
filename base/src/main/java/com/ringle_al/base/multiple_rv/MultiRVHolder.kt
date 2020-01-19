package com.ringle_al.base.multiple_rv

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

/**
 * create by 岑胜德
 * on 2019/11/14
 * 说明:
 *
 */
class MultiRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mType: Int = 0

    private val mView = SparseArray<View>()

    /**
     *采用工厂方法设计模式生产ViewHolder
     *
     */
    companion object Factory {
        operator fun invoke(
            context: Context,
            layoutId: Int,
            parent: ViewGroup,
            viewType: Int
        ): MultiRVHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return MultiRVHolder(itemView)
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mView[viewId]
        if (view == null) {
            view = this.itemView.findViewById(viewId)
            mView.put(viewId, view)
        }
        view ?: throw RuntimeException("未找到控件：${this.javaClass.simpleName}")
        return view as T


    }
}