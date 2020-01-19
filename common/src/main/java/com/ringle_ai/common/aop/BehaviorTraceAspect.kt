package com.ringle_ai.common.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
 class BehaviorTraceAspect {


    @Pointcut("execution(@com.ringle_ai.common.aop.Trace * *(..))")
    fun annotationMethod() {
    }

    @Around("annotationMethod()")
    fun aroundPoint(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val methodName = methodSignature.declaringType.simpleName//拿到方法名字
        val annotationValue=methodSignature.method.getAnnotation(Trace::class.java).tag//拿到注解里面的值


        val start = System.currentTimeMillis()//此句应在joinPoint.proceed()前一句执行才精确
        joinPoint.proceed()
        val end = System.currentTimeMillis()
        Log.e(annotationValue, "耗时:${end - start}")


    }
}