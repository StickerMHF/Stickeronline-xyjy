package com.xby.lcdata.sso.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface LoginAsyncService {

    /**
     * 登录
     *
     * @param params
     * @param handler
     */
    void login(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 用户注册
     *
     * @param params
     * @param handler
     */
    void register(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 退出
     *
     * @param params
     * @param handler
     */
    void logout(JsonObject params, Handler<AsyncResult<String>> handler);

    /**
     * 验证Token是否有效
     *
     * @param params
     * @param handler
     */
    void verifyToken(JsonObject params, Handler<AsyncResult<Boolean>> handler);

    /**
     * 刷新Token
     *
     * @param params
     * @param handler
     */
    void refreshToken(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 根据Token从Redis中获取用户信息
     *
     * @param params
     * @param handler
     */
    void getUserByToken(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    /**
     * 获取短信验证码
     *
     * @param params
     * @param handler
     */
    void getVerificationCode(JsonObject params, Handler<AsyncResult<JsonObject>> handler);



    /**
     * 登录
     *
     * @param params
     * @param handler
     */
    void loginByWechat(JsonObject params, Handler<AsyncResult<JsonObject>> handler);
}
