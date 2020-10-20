package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.DistrictService;
import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.sticker.online.tools.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @ClassName: DistrictHandler
 * @Description:
 * @Author: wangf
 * @Date: 2020/1/10 0010 15:03
 * @Version: 1.0
 **/
@RouteHandler("stickeronline")
@Api(tags = "行政区域")
public class DistrictHandler {

    private DistrictService districtService =
            AsyncServiceUtil.getAsyncServiceInstance(DistrictService.class);

    /**
     * 获取行政区域树
     *
     * @return
     */
    @RouteMapping(value = "/district/getDistrictTree", method = RouteMethod.GET)
    @ApiOperation(value = "获取行政区域树")
    public Handler<RoutingContext> getDistrictTree() {
        return ctx -> {
            districtService.getDistrictTree(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
     * 获取行政区域列表
     *
     * @return
     */
    @RouteMapping(value = "/district/getDistrict", method = RouteMethod.GET)
    @ApiOperation(value = "获取行政区域列表")
    @ApiImplicitParam(name = "adCode", value = "行政区域编码", dataType = "String", paramType = "query")
    public Handler<RoutingContext> getDistrict() {
        return ctx -> {
            districtService.getDistrict(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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

    @RouteMapping(value = "/district/getAll", method = RouteMethod.GET)
    @ApiOperation(value = "获取行政区域列表")
    @ApiImplicitParam(name = "adCode", value = "行政区域编码", dataType = "String", paramType = "query")
    public Handler<RoutingContext> getAll() {
        return ctx -> {
            districtService.getAll(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
