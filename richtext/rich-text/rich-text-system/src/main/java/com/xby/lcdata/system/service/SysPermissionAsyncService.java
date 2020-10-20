package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @FileName: com.ygj.cyryks.internet.main.stu.service
 * @Author: Sticker
 * @Date: 2019/7/5
 * @Version: 1.0
 */
@ProxyGen
public interface SysPermissionAsyncService {

    /**
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 根据菜单id来获取其对应的权限数据
     *
     * @param params
     * @param handler
     */
    void getPermRuleListByPermId(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 获取全部的权限树
     *
     * @param params
     * @param handler
     */
    void queryTreeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 添加菜单
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 编辑菜单
     *
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 查询所有子系统
     *
     * @param params
     * @param handler
     */
    void queryListBySystemType(JsonObject params, Handler<AsyncResult<JsonArray>> handler);


    /**
     * 查询角色授权
     *
     * @param params
     * @param handler
     */
    void queryRolePermission(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 保存角色授权
     *
     * @param params
     * @param handler
     */
    void saveRolePermission(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 新增角色按钮授权
     *
     * @param params
     * @param handler
     */
    void addRoleBtn(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    void getUserPermissionByTokenOfSystem(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 批量删除
     *
     * @param params
     * @param handler
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除
     *
     * @param params
     * @param handler
     */
    void delete(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 添加菜单权限数据
     *
     * @param params
     * @param handler
     */
    void addPermissionRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 编辑菜单权限数据
     *
     * @param params
     * @param handler
     */
    void editPermissionRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除菜单权限数据
     *
     * @param params
     * @param handler
     */
    void deletePermissionRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 查询菜单权限数据
     *
     * @param params
     * @param handler
     */
    void queryPermissionRule(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * @param params
     * @param handler
     */
    void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 添加数据权限
     *
     * @param params
     * @param handler
     */
    void addDataRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 修改数据权限
     *
     * @param params
     * @param handler
     */
    void editDataRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除数据权限
     *
     * @param params
     * @param handler
     */
    void delDataRule(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 获取数据权限
     *
     * @param params
     * @param handler
     */
    void getDataRule(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 保存SVG
     *
     * @param params
     * @param handler
     */
    void saveSvg(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 获取SVG
     *
     * @param params
     * @param handler
     */
    void getSvgList(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 删除SVG
     *
     * @param params
     * @param handler
     */
    void delSvg(JsonObject params, Handler<AsyncResult<String>> handler);

}
