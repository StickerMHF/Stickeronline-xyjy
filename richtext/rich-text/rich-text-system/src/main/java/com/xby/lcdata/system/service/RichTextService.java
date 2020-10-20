package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * @InterfaceName: RichTextService
 * @Description:
 * @Author: wangf
 * @Date: 2020/10/19 001916:16
 * @Version: 1.0
 **/
@ProxyGen
public interface RichTextService {

    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    void add(JsonObject params, Handler<AsyncResult<String>> handler);

    void edit(JsonObject params, Handler<AsyncResult<String>> handler);

    void delete(JsonObject params, Handler<AsyncResult<String>> handler);

}
