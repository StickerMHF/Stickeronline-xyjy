package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysLoginLogService;
import com.xby.lcdata.system.service.SysOperationLogService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @ClassName: SysLoginLogHandler
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:10
 * @Version: 1.0
 **/
@RouteHandler("stickeronline/sys/log")
@Api(tags = "登录日志管理")
public class SysLoginLogHandler {

    private SysLoginLogService sysLoginLogService = AsyncServiceUtil.getAsyncServiceInstance(SysLoginLogService.class);
    private SysOperationLogService sysOperationLogService = AsyncServiceUtil.getAsyncServiceInstance(SysOperationLogService.class);

    /**
     * 登录日志列表（登录日志与操作日志）
     *
     * @return
     */
    @RouteMapping(value = "/logList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "分页获取登录日志列表")
    public Handler<RoutingContext> logList() {
        return ctx -> {
            JsonObject params = CommonUtil.createCondition(ctx.request(), ctx.getBody());
            if (params.getString("logType").equals("1")) {
                sysLoginLogService.list(params, res -> {
                    if (res.succeeded()) {
                        HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                                ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                    } else {
                        HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                                ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                    }
                });
            } else if(params.getString("logType").equals("2")) {
                sysOperationLogService.list(params, res -> {
                    if (res.succeeded()) {
                        HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                                ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("succeed"));
                    } else {
                        HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                                ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                    }
                });
            } else {
                HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                        ReplyObj.build().setSuccess(false).setResult("请传递日志类型logType"));
            }

        };
    }

    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "分页获取登录日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "ip", value = "ip地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
    })
    public Handler<RoutingContext> list() {
        return ctx -> {
            sysLoginLogService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/deleteBatch", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除登录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "日志id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysLoginLogService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
