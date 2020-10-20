package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * @FileName: com.sticker.online.main.system.service
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/21
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@ProxyGen
public interface SysAnnouncementSendAsyncService {
    /**
     * 我的消息列表（分页查询）
     *
     * @param params
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 根据id查询
     *
     * @param params
     */
    void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 已读
     *
     * @param params
     */
    void read(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 一键已读
     *
     * @param params
     */
    void readAll(JsonObject params, Handler<AsyncResult<String>> handler);
}
