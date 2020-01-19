package com.ringle_al.base.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *create by 岑胜德
 *on 2019/11/13
 *说明:创建 RViewHelper 类所需数据,它的实现类很容易创建RViewHelper 对象
 *
 */
interface RViewCreate<T> {

    fun context(): Context


    fun createSwipeRefresh(): SwipeRefreshLayout
    fun createRecyclerView(): RecyclerView
    fun createAdapter(): RViewAdapter<T>


    fun isSupportPaging(): Boolean//是否支持分页


}