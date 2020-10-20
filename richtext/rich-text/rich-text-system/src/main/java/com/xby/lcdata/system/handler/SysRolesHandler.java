package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysRolesAsyncService;
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

import static java.net.HttpURLConnection.*;

/**
 * @FileName: com.sticker.online.main.system.handler
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@RouteHandler("stickeronline/sys/role")
@Api(tags = "角色管理")
public class SysRolesHandler {

    private SysRolesAsyncService sysRolesAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysRolesAsyncService.class);

    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body"),
    })
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysRolesAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页长", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "Integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Integer", paramType = "query", required = true)
    })
    public Handler<RoutingContext> queryPageList() {
        return ctx -> {
            sysRolesAsyncService.queryPageList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "编辑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "description", value = "描述", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createBy", value = "创建人", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "createTime", value = "创建时间", dataType = "String", paramType = "body", required = true),
            @ApiImplicitParam(name = "updateBy", value = "更新人", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", dataType = "String", paramType = "body")
    })
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysRolesAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", dataType = "String", paramType = "query", required = true)
    })
    public Handler<RoutingContext> delete() {
        return ctx -> {
            sysRolesAsyncService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
    @ApiOperation(value = "查询菜单权限树-授权，需修改为按照当前用户权限查询")
    public Handler<RoutingContext> queryTreeList() {
        return ctx -> {
            sysRolesAsyncService.queryTreeList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/queryall", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询所有角色")
    public Handler<RoutingContext> queryall() {
        return ctx -> {
            sysRolesAsyncService.queryall(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
