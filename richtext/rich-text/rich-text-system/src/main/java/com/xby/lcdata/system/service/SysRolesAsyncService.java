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
public interface SysRolesAsyncService {
    /**
     * 新增用户
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 分页查询角色列表
     *
     * @param params
     * @param handler
     */
    void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 编辑
     *
     * @param params
     * @param handler
     */

    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 通过id删除
     *
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 用户角色授权功能，查询菜单权限树
     *
     * @param params
     * @param handler
     */
    void queryTreeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 查询所有角色
     *
     * @param params
     * @param handler
     */
    void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler);
}
