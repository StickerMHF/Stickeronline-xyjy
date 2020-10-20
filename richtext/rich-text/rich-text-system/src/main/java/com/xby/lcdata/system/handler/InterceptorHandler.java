package com.xby.lcdata.system.handler;

import com.alibaba.fastjson.JSONObject;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.constants.Constants;
import com.tb.base.common.util.HttpUtils;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.*;

/**
 * @ClassName: InterceptorHandler
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/13 0013 9:12
 * @Version: 1.0
 **/

@RouteHandler(order = 3)
public class InterceptorHandler {

    //系统管理
    @RouteMapping(value = "stickeronline/sys/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationSysGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sys/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationSysPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sys/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationSysDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sys/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationSysPut() {
        return ctx -> {
            next(ctx);
        };
    }

    //    系统
    @RouteMapping(value = "stickeronline/system/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationSystemGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/system/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationSystemPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/system/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationSystemPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/system/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationSystemDelete() {
        return ctx -> {
            next(ctx);
        };
    }


    //    部门
    @RouteMapping(value = "stickeronline/sysdepart/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationSysdepart() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sysdepart/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationSysdepartPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sysdepart/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationSysdepartPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/sysdepart/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationSysdepartDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    日志
    @RouteMapping(value = "stickeronline/log/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationLogGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/log/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationLogPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/log/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationLogPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "stickeronline/log/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationLogDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    工作流
    @RouteMapping(value = "act/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationActGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "act/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationActPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "act/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationActPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "act/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationActDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    表单设计
    @RouteMapping(value = "form/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationFormGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "form/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationFormPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "form/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationFormPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "form/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationFormDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    在线表单
    @RouteMapping(value = "formonline/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationFormOnlineGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "formonline/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationFormOnlinePost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "formonline/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationFormOnlinePut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "formonline/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationFormOnlineDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    任务管理
    @RouteMapping(value = "platform/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationPlatFormGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "platform/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationPlatFormPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "platform/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationPlatFormPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "platform/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationDataServicesDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    数据服务
    @RouteMapping(value = "dataservices/release/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationDataServicesGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "dataservices/release/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationDataServicesPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "dataservices/release/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationDataServicesPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "dataservices/release/releaseorm/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationPlatFormDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    //    数据源管理
    @RouteMapping(value = "servers/*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticationServersGet() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "servers/*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticationServersPost() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "servers/*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticationServersPut() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "servers/*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticationServersDelete() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "tb/wxUser*", method = RouteMethod.GET, order = 3)
    public Handler<RoutingContext> authenticwechatGET() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "tb/wxUser*", method = RouteMethod.PUT, order = 3)
    public Handler<RoutingContext> authenticwechatPUT() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "tb/wxUser*", method = RouteMethod.POST, order = 3)
    public Handler<RoutingContext> authenticwechatPOST() {
        return ctx -> {
            next(ctx);
        };
    }

    @RouteMapping(value = "tb/wxUser*", method = RouteMethod.DELETE, order = 3)
    public Handler<RoutingContext> authenticwechatDELETE() {
        return ctx -> {
            next(ctx);
        };
    }

    public void next(RoutingContext ctx) {
        String result = verifyToken(ctx.request());
        if (oConvertUtils.isEmpty(result)) {
            try {
                ctx.next();
            } catch (Exception e) {
                e.printStackTrace();
                HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST, ReplyObj.build().setSuccess(false).setMsg(e.getMessage()));
            }
        } else {
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST, ReplyObj.build().setSuccess(false).setMsg(result));
        }
    }

    /**
     * 验证Token
     *
     * @param request
     * @return
     */
    public String verifyToken(HttpServerRequest request) {
        String token = request.getHeader("X-Access-Token");
        if (oConvertUtils.isNotEmpty(token)) {
            Map<String, String> params = new HashMap<>();
            params.put("token", token);
            JSONObject resultJson = null;
            try {
                resultJson = HttpUtils.sendGet(params, Constants.verificationUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return "验证地址不可用!" + Constants.verificationUrl;
            }
            if (resultJson.getBoolean("result")) {
                return null;
            } else {
                return "X-Access-Token已失效!";
            }
        } else {
            return "X-Access-Token不存在!";
        }
    }

}
