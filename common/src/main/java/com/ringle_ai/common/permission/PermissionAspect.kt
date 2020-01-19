package com.ringle_ai.common.permission

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ringle_ai.common.base.BaseApplication
import com.ringle_ai.common.permission.annotation.Permission
import com.ringle_ai.common.permission.annotation.PermissionCanceled
import com.ringle_ai.common.permission.annotation.PermissionDenied
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.lang.reflect.Method
import kotlin.contracts.Returns
import kotlin.reflect.KClass

/*
*
* 基本语法

环境配置完成后，我们需要用AspectJ注解编写切面代码。
•@Aspect 用它声明一个类，表示一个需要执行的切面。
•@Pointcut 声明一个切点。
•@Before/@After/@Around/...（统称为Advice类型） 声明在切点前、后、中执行切面代码。

这么说你可能有点蒙，我们换个角度解释。

假设你是一个AOP框架的设计者，最先需要理清的其基本组成要素。既然需要做代码织入那是不是一定得配置代码的织入点呢？
* 这个织入点就是Pointcut，有了织入点我们还需要指定具体织入的代码，这个代码写在哪里呢？
* 就是写在以@Before/@After/@Around注解的方法体内。有了织入点和织入代码，还需要告诉框架自己是一个面向切面的配置文件，
* 这就需要使用@Aspect声明在类上。

*
* */
@Aspect
class PermissionAspect {

      companion object{
        private  const val TAG="PermissionAspect==>"
      }
    /*
    * 插入代码的目标点(如在Activity中被@Permission注解标记的方法)
    * */
    @Pointcut("execution(@com.ringle_ai.common.permission.annotation.Permission * *(..)) && @annotation(permission)")
    fun requestPermission(permission: Permission) {

    }

    /*
    * joinPoint:切点的上下文信息
    *
    * */
    @RequiresApi(Build.VERSION_CODES.M)
    @Around("requestPermission(permission)")
    fun aroundJointPoint(joinPoint: ProceedingJoinPoint, permission: Permission)  {
        var context: Context? = null
        val any = joinPoint.`this`
        //判断joinPoint.`this是 Activity 还是 Fragment,拿到Context引用
        when (any) {
            is Context -> context = any
            is Fragment -> context = any.context!!
            is android.app.Fragment -> context = any.context!!
        }

        /*发起权限申请
        * */
        PermissionActivity.requestPermission(
            context,
            permission.permissions,
            permission.requestCode,
            object : IPermission {
                //已授权
                override fun granted() {
                    //执行目标方法
                    joinPoint.proceed()
                    Log.e(TAG,"执行目标方法")

                }

                //取消授权,但未点击不再提示
                override fun canceled() {
                    Log.e(TAG,"取消授权,但未点击不再提示")
                    any.invokAnnotation(PermissionCanceled::class.java)
                }

                ////取消授权,点击不再提示
                override fun denied() {
                    Log.e(TAG,"取消授权,点击不再提示${permission}")
                    any.invokAnnotation(PermissionDenied::class.java)
                }


            })

    }

    /*
    * 反射获取Activity/Fragment中注解方法并执行
    * (注意:扩展函数接受者类型不能写死Context,因为也可能是Fragment)
    * */
    private fun Any.invokAnnotation(annotationClass: Class<out Annotation>) {

        val methods = this.javaClass.declaredMethods
        methods.forEach continuing@ {
            //判断当前方法是否有@PermissionCancel注解
            val isHasAnnotation = it.isAnnotationPresent(annotationClass)
            if (!isHasAnnotation) return@continuing //下一轮

            it.isAccessible = true

            it.invoke(this)

        }


    }
}


