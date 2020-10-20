package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysAnnouncementAsyncService;
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
 * @Description: 系统通告表
 * @Author: Sticker
 * @Date: 2019/7/28
 * @Version: 1.0
 */
@RouteHandler("stickeronline/sys/annountCement")
@Api(tags = "通知公告管理")
public class SysAnnouncementHandler {
    private SysAnnouncementAsyncService sysAnnouncementAsyncService = AsyncServiceUtil
            .getAsyncServiceInstance(SysAnnouncementAsyncService.class);

    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query") })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            sysAnnouncementAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "titile", value = "标题", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgContentr", value = "消息内容", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "priority", value = "优先级（L低，M中，H高）", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgCategor", value = "消息类型1:通知公告2:系统消息", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgType", value = "通告对象类型（USER:指定用户，ALL:全体用户）", dataType = "String", paramType = "body", required = true), })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysAnnouncementAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 编辑
     *
     * @return
     */
    @RouteMapping(value = "/edit", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "titile", value = "标题", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgContentr", value = "消息内容", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sender", value = "发送人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "priority", value = "优先级（L低，M中，H高）", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgCategor", value = "消息类型1:通知公告2:系统消息", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "msgType", value = "通告对象类型（USER:指定用户，ALL:全体用户）", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sendStatus", value = "发布状态（0未发布，1已发布，2已撤销）", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "sendTime", value = "发布时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "cancelTime", value = "创建时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "delFlag", value = "删除状态", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true) })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysAnnouncementAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/delete", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "通过id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> delete() {
        return ctx -> {
            sysAnnouncementAsyncService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "通过id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysAnnouncementAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "通过id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> queryById() {
        return ctx -> {
            sysAnnouncementAsyncService.queryById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/doReleaseData", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "发布消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> doReleaseData() {
        return ctx -> {
            sysAnnouncementAsyncService.doReleaseData(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/doReovkeData", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "撤销消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "String", paramType = "query", required = true) })
    public Handler<RoutingContext> doReovkeData() {
        return ctx -> {
            sysAnnouncementAsyncService.doReovkeData(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 补充用户数据，并返回系统消息
     *
     * @return
     */
    @RouteMapping(value = "/listByUser", method = RouteMethod.GET, order = 1)
    public Handler<RoutingContext> listByUser() {
        return ctx -> {
            sysAnnouncementAsyncService.listByUser(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
