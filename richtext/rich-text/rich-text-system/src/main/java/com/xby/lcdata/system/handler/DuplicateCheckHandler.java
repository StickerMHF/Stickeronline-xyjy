package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysDictAsyncService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @FileName: com.sticker.online.main.system.handler
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/duplicate")
public class DuplicateCheckHandler {
    private SysDictAsyncService sysDictAsyncService = AsyncServiceUtil.getAsyncServiceInstance(SysDictAsyncService.class);
    /**
     * 校验数据是否在系统中是否存在
     */
    @RouteMapping(value = "/check", method = RouteMethod.GET, order = 1)
    public Handler<RoutingContext> doDuplicateCheck() {
        return ctx -> {
            sysDictAsyncService.doDuplicateCheck(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res->{
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_BAD_REQUEST,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };

    }
}
