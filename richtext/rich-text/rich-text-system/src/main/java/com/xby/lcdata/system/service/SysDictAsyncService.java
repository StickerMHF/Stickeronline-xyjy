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
public interface SysDictAsyncService {
    /**
     * 获取字典列表
     *
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：获取树形字典数据
     */
    void treeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：新增
     */
    void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：编辑
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

    /**
     * @功能：导出excel
     */
    void exportXls(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @功能：通过excel导入数据
     */
    void importExcel(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 获取字典数据
     */
    void getDictItems(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 获取字典数据
     *
     * @param params
     * @return
     */
    void getDictItemsByKey(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 校验数据是否在系统中是否存在
     *
     * @return
     */
    void doDuplicateCheck(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 根据字典名称与字典编号查询字典列表
     */
    void queryByNameCode(String dictName, String dictCode, Handler<AsyncResult<JsonObject>> handler);

    void getDataBySql(JsonObject params, Handler<AsyncResult<JsonArray>> handler);
}
