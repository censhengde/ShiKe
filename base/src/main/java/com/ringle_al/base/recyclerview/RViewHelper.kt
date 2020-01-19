package com.ringle_al.base.recyclerview

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *create by 岑胜德
 *on 2019/11/13
 *说明:
 *
 */
class RViewHelper<T> private constructor(val builder: Builder<T>) {

    private lateinit var context: Context
    private lateinit var swipeFresh: SwipeRefreshLayout//下拉控件
    private var swipeRefreshHelper: SwipeRefreshHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var rViewAdapter: RViewAdapter<T>
    private var startPageNum = 1//起始页码
    private var isSupportPaging = false//是否支持加载更多
    private lateinit var listener: SwipeRefreshHelper.SwipeRefreshListener
    private var currentPage = 0//当前页数

    init {
        this.context = builder.create.context()
        this.swipeFresh = builder.create.createSwipeRefresh()
        this.recyclerView = builder.create.createRecyclerView()
        this.rViewAdapter = builder.create.createAdapter()
        this.isSupportPaging = builder.create.isSupportPaging()
        this.listener = builder.listener
        this.currentPage = this.startPageNum

        swipeRefreshHelper = SwipeRefreshHelper(swipeFresh)
        init()
    }

    private fun init() {
        //recyclerview初始化
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()

        //下拉刷新
        swipeRefreshHelper.setSwipeRefreshListener(object : SwipeRefreshHelper.SwipeRefreshListener {
            override fun onRefresh() {


            }

        })


    }

    fun nitifyAdapterDataSetChanged(datas: MutableList<T>) {
        //如果首页加载,下拉刷新
        if (currentPage == startPageNum) {
            rViewAdapter.updataDatas(datas)
        } else {
            rViewAdapter.addDatas(datas)
        }
        recyclerView.adapter = rViewAdapter
    }

    class Builder<T>(
        var create: RViewCreate<T>,
        var listener: SwipeRefreshHelper.SwipeRefreshListener
    ) {

        fun build(): RViewHelper<T> {
            return RViewHelper(this)
        }
    }

}