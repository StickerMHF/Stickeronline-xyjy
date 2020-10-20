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
public interface SysAnnouncementAsyncService {
    /**
     * 获取系统消息列表
     *
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：新增
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * @功能：编辑
     */
    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * @功能：删除
     */
    void delete(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * @功能：批量删除
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 通过id查询
     *
     * @return
     */
    void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 更新发布操作
     *
     * @return
     */
    void doReleaseData(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 更新撤销操作
     *
     * @return
     */
    void doReovkeData(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * @return
     * @功能：补充用户数据，并返回系统消息
     */
    void listByUser(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
