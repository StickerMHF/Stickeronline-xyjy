package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysAnnouncementSendAsyncService;
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
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @FileName: com.sticker.online.main.system.handler
 * @Description:用户通告阅读标记表
 * @Author: Sticker
 * @Date: 2019/7/28
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/sysAnnouncementSend")
@Api(tags = "我的消息")
public class SysAnnouncemenSendHandler {

    private SysAnnouncementSendAsyncService sysAnnouncementSendAsyncService = AsyncServiceUtil
            .getAsyncServiceInstance(SysAnnouncementSendAsyncService.class);

    /**
     * 分页列表查询
     *
     * @return
     */
    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "sender", value = "发送人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query") })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            sysAnnouncementSendAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryById", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> queryById() {
        return ctx -> {
            sysAnnouncementSendAsyncService.queryById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/read", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "设置已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息发布id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> read() {
        return ctx -> {
            sysAnnouncementSendAsyncService.read(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    /**
     * 一键已读
     */
    @RouteMapping(value = "/readAll", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "一键已读")
    public Handler<RoutingContext> readAll() {
        return ctx -> {
            sysAnnouncementSendAsyncService.readAll(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
