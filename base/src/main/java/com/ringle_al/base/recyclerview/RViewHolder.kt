package com.ringle_al.base.recyclerview

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

/**
 * create by 岑胜德
 * on 2019/11/12
 * 说明:多样式布局通用ViewHolder
 * 功能:提供一个能让外界通过viewId得到相对应的View的接口
 *
 */
 class RViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        //创建ViewHolder
        fun create(context: Context, parent: ViewGroup, layoutId: Int): RViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return RViewHolder(itemView)
        }
    }

    //以viewId为key,view为value
    private val mViews = SparseArray<View>()//缓存控件


    /**
     * 通过viewId拿到View,
     * 有则直接从缓存拿
     * 没有则创建
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(viewId: Int): T {
        var view: T? = mViews.get(viewId) as T
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        view?:throw RuntimeException("未找到控件")
        return view
        /**
         * 思考:为什么不直接return itemView.findViewById(viewId)呢?
         *
         */

    }


    /**
     *
     *
     */
    fun getConvertView(): View = itemView

}