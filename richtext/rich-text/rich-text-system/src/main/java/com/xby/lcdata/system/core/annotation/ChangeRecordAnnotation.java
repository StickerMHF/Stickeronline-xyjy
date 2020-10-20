package com.xby.lcdata.system.core.annotation;

import com.xby.lcdata.system.core.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//设置注解在方法上使用
@Retention(RetentionPolicy.RUNTIME)//设置注解保留在运行时
public @interface ChangeRecordAnnotation {

    //操作类型
    OperationType operationType() default OperationType.GET;

    //实体
    Class entity();

}
