package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysDepartAsyncService;
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
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sysdepart/sysDepart")
@Api(tags = "部门管理")
public class SysDepartHandler {

    private SysDepartAsyncService sysDepartAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysDepartAsyncService.class);

    @RouteMapping(value = "/queryIdTree", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "加载所有部门树")
    public Handler<RoutingContext> queryIdTree() {
        return ctx -> {
            sysDepartAsyncService.queryIdTree(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryTreeList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取部门树")
    public Handler<RoutingContext> queryTreeList() {
        return ctx -> {
            sysDepartAsyncService.queryTreeList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "新增部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级is", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "departName", value = "部门名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "departNameEn", value = "英文名", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "departNameAbbr", value = "缩写", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "departOrder", value = "排序", dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "orgType", value = "部门类型 1一级部门 2子部门", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "fax", value = "传真", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "address", value = "地址", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "memo", value = "备注", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysDepartAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "编辑部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "parentId", value = "父级is", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "departName", value = "部门名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "departNameEn", value = "英文名", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "departNameAbbr", value = "缩写", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "departOrder", value = "排序", dataType = "Integer", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "orgType", value = "部门类型 1一级部门 2子部门", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "fax", value = "传真", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "address", value = "地址", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "memo", value = "备注", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "status", value = "状态（1启用，0不启用）", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "delFlag", value = "删除状态（0，正常，1已删除）", dataType = "String", paramType = "", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建日期", dataType = "String", paramType = "body", required = true),
    })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysDepartAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "批量删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "部门id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysDepartAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/searchBy", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "搜索部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departName", value = "部门名称", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> searchBy() {
        return ctx -> {
            sysDepartAsyncService.searchBy(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
