package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * @FileName: com.sticker.online.springvertx.main.service.impl
 * @Author: Sticker
 * @Date: 2019/6/1
 * @Version: 1.0
 * @Desc:用户表 服务实现类
 */
@ProxyGen
public interface SysAccessoryAsyncService {
    /**
     * 获取文件列表
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 新增附件
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 编辑附件
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 删除附件
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 批量删除附件
     * @param params
     * @param handler
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
