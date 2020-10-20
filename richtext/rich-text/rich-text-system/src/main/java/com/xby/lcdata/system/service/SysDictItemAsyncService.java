package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
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
public interface SysDictItemAsyncService {
    /**
     * 获取字典列表
     *
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 根据dictId查询字典数据
     *
     * @param dictId
     * @return
     */
    void querrByDicId(String dictId, String itemText, Integer status, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 新增
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 编辑
     *
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：删除
     */
    void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：批量删除
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
