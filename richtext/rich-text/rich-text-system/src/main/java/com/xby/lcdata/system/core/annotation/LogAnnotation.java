package com.xby.lcdata.system.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: LogAnnotation
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:48
 * @Version: 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    String operationType();

}
