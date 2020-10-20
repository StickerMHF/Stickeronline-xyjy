package com.xby.lcdata.system.handler;

import com.xby.lcdata.system.service.DataChangeRecordService;
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
import io.vertx.ext.web.RoutingContext;

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
@Api(tags = "变更记录")
public class DataChangeRecordtHandler {

    private DataChangeRecordService dataChangeRecordService =
            AsyncServiceUtil.getAsyncServiceInstance(DataChangeRecordService.class);

    /**
     * 获取变更记录
     *
     * @return
     */
    @RouteMapping(value = "/log/getChangeRecord", method = RouteMethod.GET)
    @ApiOperation(value = "获取数据变更记录")
    public Handler<RoutingContext> getChangeRecord() {
        return ctx -> {
            dataChangeRecordService.getChangeRecord(CommonUtil.createCondition(ctx.request(), ctx.getBody()), res -> {
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
