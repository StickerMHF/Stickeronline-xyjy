package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.SysDictItemAsyncService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.core.utils.StringUtil;
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
@RouteHandler("stickeronline/sys/dictItem")
@Api(tags = "字典数据管理")
public class SysDictItemHandler {

    private SysDictItemAsyncService sysDictItemAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(SysDictItemAsyncService.class);

    /**
     * 删除
     */
    @RouteMapping(value = "/delete", method = RouteMethod.DELETE, order = 1)
    @ApiOperation(value = "删除")
    public Handler<RoutingContext> delete() {
        return ctx -> {
            sysDictItemAsyncService.delete(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
            sysDictItemAsyncService.deleteBatch(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     */
    @RouteMapping(value = "/edit", method = RouteMethod.PUT, order = 1)
    @ApiOperation(value = "编辑")
    public Handler<RoutingContext> edit() {
        return ctx -> {
            sysDictItemAsyncService.edit(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("查询成功"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    /**
     * 新增
     */
    @RouteMapping(value = "/add", method = RouteMethod.POST, order = 1)
    @ApiOperation(value = "新增")
    public Handler<RoutingContext> add() {
        return ctx -> {
            sysDictItemAsyncService.add(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("查询成功"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    /**
     * 查询字典数据
     */
    @RouteMapping(value = "/list", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "查询字典数据")
    public Handler<RoutingContext> list() {
        return ctx -> {
            sysDictItemAsyncService.list(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("查询成功"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }

    /**
     * 根据dictId查询字典数据
     */
    @RouteMapping(value = "/queryByDictId", method = RouteMethod.GET, order = 1)
    @ApiOperation(value = "根据dictId查询字典数据")
    public Handler<RoutingContext> queryByDictId() {
        return ctx -> {
            JsonObject params = CommonUtil.createCondition(ctx.request(), ctx.getBody());
            String itemText = StringUtil.getParam(params.getString("itemText"), "");
            Integer status = StringUtil.getParamToInt(params.getString("status"), -1);
            sysDictItemAsyncService.querrByDicId(params.getString("dictId"), itemText, status, res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(true).setResult(res.result()).setMsg("查询成功"));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setSuccess(false).setMsg(res.cause().getMessage()));
                }
            });
        };
    }


}
