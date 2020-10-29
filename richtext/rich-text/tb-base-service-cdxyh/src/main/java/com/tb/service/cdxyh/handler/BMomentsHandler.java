package com.tb.service.cdxyh.handler;

import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import com.tb.service.cdxyh.service.BAlumnusMemberService;
import com.tb.service.cdxyh.service.BMomentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

@RouteHandler("stickeronline/moments")
@Api(tags = "朋友圈动态表")
public class BMomentsHandler {
    private BMomentsService bMomentsService = AsyncServiceUtil.getAsyncServiceInstance(BMomentsService.class);

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "发布朋友圈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "发布者姓名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "photos", value = "照片列表", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "content", value = "发布内容", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userPhoto", value = "发布者头像", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "userId", value = "发布者ID", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            bMomentsService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "获取朋友圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "order", value = "排序", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            bMomentsService.queryPageList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "queryById", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询成员详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成员ID", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryById(){
        return ctx -> {
            bMomentsService.queryById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
              if (res.succeeded()){
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
