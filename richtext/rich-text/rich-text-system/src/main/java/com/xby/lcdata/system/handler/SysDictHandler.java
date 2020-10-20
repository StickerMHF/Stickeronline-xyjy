package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysDictAsyncService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
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
@RouteHandler("stickeronline/sys/dict")
@Api(tags = "字典管理")
public class SysDictHandler {

    private SysDictAsyncService sysDictAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysDictAsyncService.class);

    /**
     * 获取字典数据
     */
    @RouteMapping(value = "/getDictText/:dictCode/:key", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取字典数据")
    public Handler<RoutingContext> getDictText() {
        return ctx -> {
            sysDictAsyncService.getDictItemsByKey(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 批量删除
     */
    @RouteMapping(value = "/deleteBatch", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "批量删除")
    public Handler<RoutingContext> deleteBatch() {
        return ctx -> {
            sysDictAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 删除
     */
    @RouteMapping(value = "/delete", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除")
    public Handler<RoutingContext> delete() {
        return ctx -> {
            sysDictAsyncService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 编辑字典
     */
    @RouteMapping(value = "/edit", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑字典")
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysDictAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 新增字典
     */
    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增字典")
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysDictAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 获取树形字典数据
     */
    @RouteMapping(value = "/treeList", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取树形字典数据")
    public Handler<RoutingContext> treeList() {
        return ctx -> {
            sysDictAsyncService.treeList(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 获取字典列表
     */
    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取字典列表")
    public Handler<RoutingContext> list() {
        return ctx -> {
            sysDictAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 获取字典数据
     */
    @RouteMapping(value = "/getDictItems/:dictCode", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "获取字典数据")
    public Handler<RoutingContext> getDictItems() {
        return ctx -> {
            sysDictAsyncService.getDictItems(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 根据字典名称与字典编号查询字典列表
     */
    @RouteMapping(value = "/queryByNameCode", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据字典名称与字典编号查询字典列表")
    public Handler<RoutingContext> queryByNameCode() {
        return ctx -> {
            JsonObject params = CommonUtil.createCondition(ctx.request(), ctx.getBody());
            sysDictAsyncService.queryByNameCode(params.getString("dictName"), params.getString("dictCode"), res -> {
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
