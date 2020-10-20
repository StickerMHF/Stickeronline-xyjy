package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface SysLoginLogService {

    void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler);

    void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler);

}
