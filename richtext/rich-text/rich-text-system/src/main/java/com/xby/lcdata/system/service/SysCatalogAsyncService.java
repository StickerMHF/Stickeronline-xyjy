package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @FileName: com.sticker.online.springvertx.main.service.impl
 * @Author: Sticker
 * @Date: 2019/6/1
 * @Version: 1.0
 * @Desc:用户表 服务实现类
 */
@ProxyGen
public interface SysCatalogAsyncService {

    void list(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 获取目录树
     * @param params
     * @param handler
     */
    void queryTreeList(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 新增目录
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 编辑目录
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 删除目录
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 批量删除目录
     * @param params
     * @param handler
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
     */
    void queryIdTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler);
}
