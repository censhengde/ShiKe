package com.ringle_al.base.multiple_rv

import android.util.SparseArray
import android.util.SparseIntArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * create by 岑胜德
 * on 2019/11/14
 * 说明:
 *
 */
class MultiRVAdapter<T>(datas: MutableList<T>) :
    RecyclerView.Adapter<MultiRVHolder>() {

    private lateinit var datas: MutableList<T>//adapter与bean是一对多的关系

    private lateinit var itemStyles:SparseArray<MultiItem>//adapter与MultiItem也是一对多的关系
    /**
     *分析：每个bean如何与每个MultiItem建立起关系呢？
     *因为Adapter仅提供position值来标识Item，所以为了方便访问MultiItem，应建立起
     * key=position,value=MultiItem的形式
     */

    private  var layoutIds= SparseIntArray()


    init {
        setDatas(datas)
    }

    fun setDatas(datas: MutableList<T>) {
        this.datas = datas

        for (i:Int in 0 until datas.size){
            val itemStyle=
            itemStyles.put(i,)
        }
    }

    override fun getItemViewType(position: Int): Int {
        //如果是多样式布局
        if (itemStyles.size()>0){
        return itemStyles[position].getType()
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiRVHolder {
        val layoutId=layoutIds[viewType]//如何根据viewType拿到对应layoutId？？？--》Key-Value

        return MultiRVHolder.Factory(parent.context,layoutId,parent,viewType)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    /**
     *将目标View与实体类数据绑定
     *
     */
    override fun onBindViewHolder(holder: MultiRVHolder, position: Int) {
        datas[position].convert(holder)
        TODO("not implemented") //To change body  of created functions use File | Settings | File Templates.
    }

}