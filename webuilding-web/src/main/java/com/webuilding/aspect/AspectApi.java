package com.webuilding.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *  装饰器模式
 */
public interface AspectApi {

     Object doHandlerAspect(ProceedingJoinPoint pjp, Method method)throws Throwable;

}
