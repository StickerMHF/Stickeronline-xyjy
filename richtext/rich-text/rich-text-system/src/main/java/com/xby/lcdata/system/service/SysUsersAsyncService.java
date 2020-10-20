package com.xby.lcdata.system.service;

import com.sticker.online.core.model.BaseAsyncService;
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
public interface SysUsersAsyncService extends BaseAsyncService {

    /**
     * 分页条件查询用户
     *
     * @param params
     * @param handler
     */
    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 新增用户
     *
     * @param params
     * @param handler
     */
    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 根据用户ID查询拥有的角色
     *
     * @param params
     * @param handler
     */
    void queryUserRole(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 获取用户所属部门
     *
     * @param params
     * @param handler
     */
    void userDepartList(JsonObject params, Handler<AsyncResult<JsonArray>> handler);

    /**
     * 可以校验其他 需要检验什么就传什么。。。
     *
     * @param params
     * @param handler
     */
    void checkOnlyUser(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 编辑用户
     *
     * @param params
     * @param handler
     */
    void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 修改用户绑定部门
     *
     * @param params
     * @param handler
     */
    void editUDepartIds(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 新增用户绑定部门
     *
     * @param params
     * @param handler
     */
    void addUDepartIds(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除用户
     *
     * @param params
     * @param handler
     */
    void del(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 通过id删除(批量)
     *
     * @param params
     * @param handler
     */
    void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 解冻冻结用户
     *
     * @param params
     * @param handler
     */
    void freezeThawUser(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 根据用户id查询
     *
     * @param params
     * @param handler
     */
    void queryUserById(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 修改密码
     *
     * @param params
     * @param handler
     */
    void modifyPwd(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 指定角色添加用户
     *
     * @param params
     * @param handler
     */
    void addSysUserRole(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 删除指定角色的用户关系
     *
     * @param params
     * @param handler
     */
    void delUserRole(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 根据部门获取用户列表
     *
     * @param
     * @return
     */
    void departUserList(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 给指定部门添加对应的用户
     *
     * @param params
     * @param handler
     */
    void editSysDepartWithUser(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 批量删除指定部门的用户关系
     *
     * @param params
     * @param handler
     */
    void deleteUserInDepartBatch(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 获取指定角色的用户列表
     *
     * @param params
     * @param handler
     */
    void getUserByRole(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
