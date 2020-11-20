package com.tb.service.cdxyh.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface BMomentsService {
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
     * 根据ID获取成员详情
     * @param params
     * @param handler
     */
    void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 点赞
     * @param params
     * @param handler
     */
    void likeClick(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 获取关注的成员朋友圈
     * @param params
     * @param handler
     */
    void queryByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
    /**
     * 根据校友会ID获取朋友圈
     * @param params
     * @param handler
     */
    void queryByAlumnusId(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

}
