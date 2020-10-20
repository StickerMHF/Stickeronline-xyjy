package com.xby.lcdata.sso.util;

import io.vertx.core.http.HttpServerRequest;

/**
 * @ClassName: HttpContextUtil
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 16:58
 * @Version: 1.0
 **/
public class HttpContextUtil {

    /**
     * 获取IP地址的方法
     *
     * @param request 传一个request对象下来
     * @return
     */
    public static String getIpAddress(HttpServerRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.remoteAddress().host();
        }
        return ip;
    }

}
