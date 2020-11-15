package com.tb.service.cdxyh.handler;

import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import com.tb.service.cdxyh.service.BAlumnusJoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

@RouteHandler("stickeronline/alumnusJoin")
@Api(tags = "组织")
public class BAlumnusJoinHandler {
    private BAlumnusJoinService bAlumnusJoinService =
            AsyncServiceUtil.getAsyncServiceInstance(BAlumnusJoinService.class);

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "关注组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alumnusId", value = "组织ID", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userPhoto", value = "用户头像", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "status", value = "关注状态", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            bAlumnusJoinService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/checkById", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "审核会长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alumnusId", value = "组织ID", dataType = "String", paramType = "body", required = true)
    })
    public Handler<RoutingContext> checkById() {
        return ctx -> {
            bAlumnusJoinService.checkById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/delete", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "关注组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alumnusId", value = "组织ID", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> delete() {
        return ctx -> {
            bAlumnusJoinService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "组织类型", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            bAlumnusJoinService.queryPageList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @RouteMapping(value = "/queryPresidentByUserId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPresidentByUserId() {
        return ctx -> {
            bAlumnusJoinService.queryPresidentByUserId(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @RouteMapping(value = "/applyByUserId", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true)
    })
    public Handler<RoutingContext> applyByUserId() {
        return ctx -> {
            bAlumnusJoinService.applyByUserId(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
