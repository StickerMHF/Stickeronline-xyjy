package com.xby.lcdata.system.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;

@ProxyGen
public interface FileUploadAsyncService {

    void saveFile(JsonArray files, Handler<AsyncResult<JsonArray>> resultHandler);

}
