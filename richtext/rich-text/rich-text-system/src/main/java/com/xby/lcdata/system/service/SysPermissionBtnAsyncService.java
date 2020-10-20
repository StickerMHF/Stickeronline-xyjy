package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface SysPermissionBtnAsyncService {

    /**
     * 条件查询按钮列表
     *
     * @param params
     * @param handler
     */
    void listByCondition(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 新增按钮
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 编辑按钮
     *
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除按钮
     *
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 根据菜单权限Id删除按钮
     *
     * @param params
     * @param handler
     */
    void deleteByPermissionId(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 根据菜单Id查询按钮权限
     *
     * @param params
     * @param handler
     */
    void getBtnByPermission(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 根据角色Id查询按钮权限
     *
     * @param params
     * @param handler
     */
    void getBtnByRole(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

}
