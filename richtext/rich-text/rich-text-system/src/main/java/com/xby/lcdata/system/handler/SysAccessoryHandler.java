package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysAccessoryAsyncService;
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
 * @Description:附件表
 * @Author: Sticker
 * @Date: 2019/7/28
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/accessory")
@Api(tags = "附件管理")
public class SysAccessoryHandler {

    private SysAccessoryAsyncService sysAccessoryAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysAccessoryAsyncService.class);

    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            sysAccessoryAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "新增附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catalogid", value = "节点id", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "pid", value = "父节点id", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "address", value = "访问地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "isEditor", value = "是否可编辑", dataType = "boolean", paramType = "body", required = true),
            @ApiImplicitParam(name = "filename", value = "文件名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "oldFileName", value = "源文件名", dataType = "String", paramType = "body", required = true)
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysAccessoryAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/edit", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "catalogid", value = "节点id", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "pid", value = "父节点id", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "address", value = "访问地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "type", value = "文件类型", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "isEditor", value = "是否可编辑", dataType = "boolean", paramType = "body", required = true),
            @ApiImplicitParam(name = "filename", value = "文件名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "oldFileName", value = "源文件名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "String", paramType = "body", required = true)
    })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysAccessoryAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "删除附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "附件Id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> delete() {
        return ctx -> {
            sysAccessoryAsyncService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "批量删除附件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "附件Id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysAccessoryAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
