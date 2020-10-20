package com.xby.lcdata.system.core.aop;

import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysOperationLog;
import com.xby.lcdata.system.repository.SOperationLogRepository;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.util.HttpContextUtil;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName: SOperationLogAopAspect
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:59
 * @Version: 1.0
 **/
@Component
@Aspect
@Order(3)
public class SOperationLogAopAspect {

    @Autowired
    private SOperationLogRepository sOperationLogRepository;

    /**
     * 使用环绕增强处理日志
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Exception
     */
    @Around("@annotation(com.xby.lcdata.system.core.annotation.LogAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] paramsValue = proceedingJoinPoint.getArgs();
        String[] paramsName = methodSignature.getParameterNames();
        SysOperationLog sysOperationLog = new SysOperationLog();
        String operator = "admin";
        HttpServerRequest request = null;
        for (int i = 0; i < paramsName.length; i++) {
            if (paramsName[i].equals("token")) {
                String userName = JwtUtil.getUserNameByToken(String.valueOf(paramsValue[i]));
                if (userName != null) {
                    operator = userName;
                }
            }
            if (paramsName[i].equals("request")) {
                request = (HttpServerRequest) paramsValue[i];
            }
        }
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        String operationType = logAnnotation.operationType();
        if (request != null) {
            sysOperationLog.setIp(HttpContextUtil.getIpAddress(request));
        } else {
            sysOperationLog.setIp("0.0.0.0");
        }
        sysOperationLog.setOperator(operator);
        sysOperationLog.setOparationTime(TimeUtil.getDateNow());
        sysOperationLog.setOperationType(operationType);
        Object result = null;
        System.out.println("***********************添加日志:" + Json.encode(sysOperationLog));
        try {
            proceedingJoinPoint.proceed();
            sysOperationLog.setOperationResult("成功");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            sysOperationLog.setOperationResult("失败");
        } finally {
            sOperationLogRepository.save(sysOperationLog);
        }
        return result;
    }

}
