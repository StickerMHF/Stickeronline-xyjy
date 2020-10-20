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
public interface SysDepartAsyncService {
    /**
     * 新增部门
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 编辑部门
     *
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 查询部门树
     *
     * @param params
     * @param handler
     */
    void queryTreeList(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
     * @param params
     * @param handler
     */
    void queryIdTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 删除部门（批量删除）
     *
     * @param params
     * @param handler
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 搜索部门
     *
     * @param params
     * @param handler
     */
    void searchBy(JsonObject params,Handler<AsyncResult<JsonArray>> handler);
}
