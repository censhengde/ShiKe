package com.ringle_al.base.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * create by 岑胜德
 * on 2019/11/12
 * 说明:多样式布局RecyclerView.Adapter
 *
 */
abstract class RViewAdapter<T>(datas: MutableList<T>?, item: RViewItem<T>? = null) :
    RecyclerView.Adapter<RViewHolder>() {


    private var datas: MutableList<T>//数据源

    private val mManager = RViewItemManager<T>()
    private lateinit var mListener: ItemListener<T>//事件监听


    init {

        this.datas = datas ?: ArrayList()
        mManager.addStyles(item)
    }

    /**
     * 添加数据集合(在原有数据的基础上添加)
     *
     */
    fun addDatas(datas: MutableList<T>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    /**
     *更新数据集合(覆盖原有数据集合,注意"添加"与"更新"的区别)
     *
     */
    fun updataDatas(datas: MutableList<T>) {
        this.datas = datas
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        when (mManager.getItemStyleCount() > 0) {
            //如果是多样式布局
            true -> return mManager.getItemViewType(datas.get(position), position)
            //如果是单一样式布局
            false -> return super.getItemViewType(position)
        }


    }

    /**
     *holder的类型决定了布局的样式
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val item = mManager.getRViewItem(viewType)//根据返回的viewType得到RViewItem
        val layoutId = item.setItemLayout()
        val holder = RViewHolder.create(parent.context, parent, layoutId)//layoutId决定Holder种类
        if (item.openClick()) setListener(holder)//拦截点击事件
        return holder
    }

    private fun setListener(holder: RViewHolder) {
        holder.getConvertView().setOnClickListener {
            val position = holder.adapterPosition
            mListener.onItemClick(it, datas[position], position)
        }

        holder.getConvertView().setOnLongClickListener { v ->
            val position = holder.adapterPosition
            mListener.onItemLongClick(v as View, datas.get(position), position)

            true
        }

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    /**
     * 思路:这里的业务是绑定数据,不同布局方式的"绑法"不同,即"绑法"是多态的,
     * 此时应该将""绑法""业务分离出去.而站在开发者角度看,最终的"绑法"应当是"
     * holder.view.setXXX(entity.getXXX())"的`形式,所以应当提供给开发者的参数有:
     * holder,entity,
     */
    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        mManager.convert(holder, datas.get(position), holder.adapterPosition)

    }


    fun setItemListener(listener: ItemListener<T>) {
        this.mListener = listener
    }
}