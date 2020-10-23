package com.tb.service.cdxyh.handler;

import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import com.tb.service.cdxyh.service.BAlumnusMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

@RouteHandler("stickeronline/alumnusMember")
@Api(tags = "组织成员")
public class BAlumnusMemberHandler {
    private BAlumnusMemberService bAlumnusMemberService =
            AsyncServiceUtil.getAsyncServiceInstance(BAlumnusMemberService.class);

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增组织成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body"),
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            bAlumnusMemberService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "查询组织成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "组织类型", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            bAlumnusMemberService.queryPageList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
          bAlumnusMemberService.queryById(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
