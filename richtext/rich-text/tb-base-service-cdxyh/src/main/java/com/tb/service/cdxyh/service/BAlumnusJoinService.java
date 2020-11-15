package com.tb.service.cdxyh.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface BAlumnusJoinService {
    /**
     * 新增
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 分页查询
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

    void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 通过id删除
     *
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<String>> handler);


    /**
     * 查询所有
     *
     * @param params
     * @param handler
     */
    void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler);
    /**
     * 审核会长
     *
     * @param params
     * @param handler
     */
    void checkById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 根据用户查询申请会长状态
     *
     * @param params
     * @param handler
     */
    void queryPresidentByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 申请会长
     *
     * @param params
     * @param handler
     */
    void applyByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
