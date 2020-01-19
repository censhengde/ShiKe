package com.ringle_al.base.recyclerview

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *create by 岑胜德
 *on 2019/11/13
 *说明:
 *
 */
class SwipeRefreshHelper(swipeRefresh: SwipeRefreshLayout) {

    companion object {
        private const val HOLO_ORANGE_DARK = android.R.color.holo_orange_dark
        private const val HOLO_GREEN_DARK = android.R.color.holo_green_dark
        private const val HOLO_BLUE_DARK = android.R.color.holo_blue_dark

    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var swipeRefreshListener: SwipeRefreshListener

    init {
        this.swipeRefreshLayout = swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
            HOLO_ORANGE_DARK,
            HOLO_GREEN_DARK,
            HOLO_BLUE_DARK
        )
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshListener.onRefresh()
        }

    }

    fun setSwipeRefreshListener(listener: SwipeRefreshListener) {
        this.swipeRefreshListener = listener
    }

    interface SwipeRefreshListener {
        fun onRefresh()
    }


}