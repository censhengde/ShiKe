package com.ringle_al.base.recyclerview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *create by 岑胜德
 *on 2019/11/13
 *说明:
 *
 */
abstract class RViewActivity<T> : AppCompatActivity(), RViewCreate<T>,
    SwipeRefreshHelper.SwipeRefreshListener {


    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun context(): Context {
        return this
    }

    override fun createSwipeRefresh(): SwipeRefreshLayout {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createRecyclerView(): RecyclerView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createAdapter(): RViewAdapter<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isSupportPaging(): Boolean {
        return false
    }
}