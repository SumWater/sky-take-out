package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类, 用于实现自动填充功能
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 逻辑实现方法  前置通知，在方法执行前执行
     * @param joinPoint
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException { //JoinPoint: 连接点
        log.info("开始进行公共字段自动填充...");

        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = autoFill.value();
        //获取方法参数 - 实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object object = args[0];
        //获取相应的属性值，进行判断
        LocalDateTime now = LocalDateTime.now();
        Long userId = BaseContext.getCurrentId();
        //赋值给对应的属性
        if(value == OperationType.INSERT){
            //为创建时间和更新时间赋值
            Method setCreateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,  LocalDateTime.class);
            Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,  LocalDateTime.class);
            Method setUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,  Long.class);
            Method setCreateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,  Long.class);
            //通过反射为对象赋值
            setCreateTime.invoke(object, now);
            setUpdateTime.invoke(object, now);
            setUpdateUser.invoke(object, userId);
            setCreateUser.invoke(object, userId);
        }else if(value == OperationType.UPDATE){
            Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,  LocalDateTime.class);
            Method setUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,  Long.class);
            setUpdateTime.invoke(object, now);
            setUpdateUser.invoke(object, userId);
        }

    }
}
