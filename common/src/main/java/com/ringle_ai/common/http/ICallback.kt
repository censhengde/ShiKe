package com.ringle_ai.common.http

import androidx.annotation.MainThread
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
/*
* create by 岑胜德
* on 2019-10-25
* 说明:网络请求状态回调接口
* */
interface ICallback {


    //请求成功
    @MainThread
    fun onSuccess(result: String)

    //请求失败
    @MainThread
    fun onFailure(err: String)



}

/*
目标:拿到any的类型参数T的实际类型的Class字节码
@param any :参数化类型对象
* */
fun <T> ICallback.analysisClassInfo(any: Any): Class<T> {
    //因为Class实现了Type(Type是所有类型的抽象)
    val genType = any.javaClass.genericSuperclass/**/
    //获取“泛型实例”中<>里面的“泛型变量”（也叫类型参数）的值，
    // 这个值是一个类型。因为可能有多个“泛型变量”（如：Map<K,V>）,
    // 所以返回的是一个Type[]。
    val parames: Array<Type> =
        (genType as ParameterizedType).actualTypeArguments
    //由于当前只有一个泛型T,所以第[0]个元素就是它了
    return parames[0] as Class<T>

}